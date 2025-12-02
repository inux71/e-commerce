//
//  ProductDetailsViewModel.swift
//  e-commerce
//
//  Created by Kacper Grabiec on 25/11/2025.
//

import Combine
import Foundation

class ProductDetailsViewModel: ObservableObject {
    private let networkManager: NetworkManager = NetworkManager.shared
    
    @Published var isLoading: Bool = false
    @Published var isAlertPresented: Bool = false
    @Published var message: String? = nil
    
    @MainActor
    func addToCart(productId: Int) async {
        isLoading = true
        
        defer {
            isLoading = false
        }
        
        do {
            try await networkManager.post(to: .addToCart(productId: productId))
            
            isAlertPresented = true
            message = "Product successfully added to cart."
        } catch {
            isAlertPresented = true
            
            if let error = error as? URLError, error.code.rawValue == 409 {
                message = "Product is already in cart."
            } else {
                message = error.localizedDescription
            }
        }
    }
}
