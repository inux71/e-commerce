//
//  CartView.swift
//  e-commerce
//
//  Created by Kacper Grabiec on 26/11/2025.
//

import SwiftUI

struct CartView: View {
    @StateObject private var viewModel: CartViewModel = CartViewModel()
    
    var body: some View {
        NavigationStack {
            List {
                ForEach(viewModel.cartProducts) { cartProduct in
                    let product: Product = cartProduct.product
                    
                    NavigationLink(destination: ProductDetailsView(
                        id: product.id,
                        name: product.name,
                        description: product.description,
                        price: product.price
                    )) {
                        CartItem(
                            name: product.name,
                            quantity: cartProduct.quantity,
                            price: product.price
                        )
                    }
                }
                .onDelete(perform: viewModel.removeProductFromCart)
            }
            .alert(
                viewModel.message ?? "",
                isPresented: $viewModel.isAlertPresented
            ) {
                Button("OK") {
                    viewModel.isAlertPresented = false
                    viewModel.message = nil
                }
            }
            .navigationTitle("Cart")
            .overlay {
                if viewModel.isLoading {
                    ProgressView()
                }
            }
            .overlay {
                if viewModel.isCartEmpty {
                    ContentUnavailableView(
                        "Your cart is empty",
                        systemImage: "magnifyingglass",
                        description: Text("There is no products in your cart")
                    )
                }
            }
            .task {
                await viewModel.fetchCart()
            }
        }
    }
}

#Preview {
    NavigationStack {
        CartView()
    }
}
