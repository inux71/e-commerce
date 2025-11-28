//
//  AccountViewModel.swift
//  e-commerce
//
//  Created by Kacper Grabiec on 20/11/2025.
//

import Combine
import Foundation

class AccountViewModel: ObservableObject {
    private let keychainStorageManager: StorageManager = KeychainStorageManager.shared
    private let networkManager: NetworkManager = NetworkManager.shared
    
    @Published var isLoading: Bool = false
    @Published var isAlertPresented: Bool = false
    @Published var errorMessage: String? = nil
    
    @Published var email: String = ""
    
    @MainActor
    func getCustomer() async {
        isLoading = true
        
        defer {
            isLoading = false
        }
        
        do {
            let customer: Customer = try await networkManager.get(from: .me)
            
            email = customer.email
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
