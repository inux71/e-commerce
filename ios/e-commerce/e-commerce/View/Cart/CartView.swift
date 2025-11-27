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
                // TODO: display products
            }
            .navigationTitle("Cart")
            .overlay {
                ContentUnavailableView(
                    "Your cart is empty",
                    systemImage: "magnifyingglass",
                    description: Text("There is no products in your cart")
                )
            }
        }
    }
}

#Preview {
    NavigationStack {
        CartView()
    }
}
