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
    
    @Published var isAlertPresented: Bool = false
    @Published var errorMessage: String? = nil
    
    func signOut() {
        do {
            try keychainStorageManager.delete(for: KeychainKey.accessToken)
        } catch {
            isAlertPresented = true
            errorMessage = error.localizedDescription
        }
    }
}
