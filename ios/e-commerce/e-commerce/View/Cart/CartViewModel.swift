//
//  CartViewModel.swift
//  e-commerce
//
//  Created by Kacper Grabiec on 26/11/2025.
//

import Combine
import Foundation

class CartViewModel: ObservableObject {
    private let networkManager: NetworkManager = NetworkManager.shared
    
    @Published var isLoading: Bool = false
    @Published var isAlertPresented: Bool = false
    @Published var message: String? = nil
    
    @Published var cartProducts: [CartProduct] = []
    
    var isCartEmpty: Bool {
        cartProducts.isEmpty
    }
    
    var totalPrice: Double {
        cartProducts.reduce(0.0, { result, cartProduct in
            result + Double(cartProduct.quantity) * cartProduct.product.price
        })
    }
    
    @MainActor
    func fetchCart() async {
        isLoading = true
        
        defer {
            isLoading = false
        }
        
        do {
            let cart: Cart = try await networkManager.get(from: .cart)
            
            cartProducts = cart.cartProducts
        } catch {
            isAlertPresented = true
            message = error.localizedDescription
        }
    }
    
    @MainActor
    func updateProductQuantity(productId: Int, quantity: Int) async {
        isLoading = true
        
        defer {
            isLoading = false
        }
        
        do {
            let updateCartProductQuantityRequest = UpdateCartProductQuantityRequest(quantity: quantity)
            
            try await networkManager.patch(
                to: .updateCartProductQuantity(productId: productId),
                with: updateCartProductQuantityRequest
            )
        } catch {
            isAlertPresented = true
            message = error.localizedDescription
        }
    }
    
    @MainActor
    func removeProductFromCart(at offsets: IndexSet) {
        guard let index: Int = offsets.first else { return }
        
        let productId: Int = cartProducts[index].product.id
        
        Task {
            isLoading = true
            
            defer {
                isLoading = false
            }
            
            do {
                try await networkManager.delete(at: .removeFromCart(productId: productId))
                
                cartProducts.remove(at: index)
            } catch {
                isAlertPresented = true
                message = error.localizedDescription
            }
        }
    }
}
