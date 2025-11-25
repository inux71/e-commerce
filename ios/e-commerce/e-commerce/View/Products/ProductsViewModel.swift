//
//  ProductsViewModel.swift
//  e-commerce
//
//  Created by Kacper Grabiec on 25/11/2025.
//

import Combine
import Foundation

class ProductsViewModel: ObservableObject {
    private let networkManager: NetworkManager = NetworkManager.shared
    
    @Published var isLoading: Bool = false
    @Published var isAlertPresented: Bool = false
    @Published var errorMessage: String? = nil
    
    @Published var products: [Product] = []
    
    @Published private(set) var nextPageIndex: Int = 0
    @Published private(set) var totalPages: Int = 1
    
    var shouldLoadMore: Bool {
        nextPageIndex < totalPages
    }
    
    @MainActor
    func getProducts() async {
        isLoading = true
        
        defer {
            isLoading = false
        }
        
        do {
            let queryItems: [URLQueryItem] = [
                URLQueryItem(name: "page", value: "\(nextPageIndex)")
            ]
            
            let response: Page<Product> = try await networkManager.get(
                from: .products,
                attach: queryItems
            )
            
            products.append(contentsOf: response.content)
            totalPages = response.page.totalPages
            
            nextPageIndex += 1
        } catch {
            isAlertPresented = true
            errorMessage = error.localizedDescription
        }
    }
}
