//
//  AccountViewModel.swift
//  e-commerce
//
//  Created by Kacper Grabiec on 20/11/2025.
//

import Combine
import Foundation

class AccountViewModel: ObservableObject {
    private let keychainStorageManager = KeychainStorageManager.shared
    private let networkManager: NetworkManager = NetworkManager.shared
    
    @Published var isLoading: Bool = false
    @Published var isAlertPresented: Bool = false
    @Published var errorMessage: String? = nil
    
    @Published var email: String = ""
    @Published var addresses: Int = 0
    
    @MainActor
    func getCustomer() async {
        isLoading = true
        
        defer {
            isLoading = false
        }
        
        do {
            let customer: Customer = try await networkManager.get(from: .me)
            
            email = customer.email
            addresses = customer.addresses
        } catch {
            isAlertPresented = true
            errorMessage = error.localizedDescription
        }
    }
    
    func signOut() {
        do {
            try keychainStorageManager.delete(for: KeychainKey.accessToken)
        } catch {
            isAlertPresented = true
            errorMessage = error.localizedDescription
        }
    }
}
