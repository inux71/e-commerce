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
}
