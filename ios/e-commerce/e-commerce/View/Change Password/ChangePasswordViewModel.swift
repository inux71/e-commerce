//
//  ChangePasswordViewModel.swift
//  e-commerce
//
//  Created by Kacper Grabiec on 21/11/2025.
//

import Combine
import Foundation

class ChangePasswordViewModel: ObservableObject {
    private let networkManager: NetworkManager = NetworkManager.shared
    
    @Published var isLoading: Bool = false
    @Published var isAlertPresented: Bool = false
    @Published var message: String? = nil
    
    @Published var password: String = ""
    @Published var passwordErrorMessage: String? = nil
    
    private var passwordValid: Bool {
        password.trimmingCharacters(in: .whitespacesAndNewlines).count >= 8
    }
    
    private func validatePassword() {
        password = password.trimmingCharacters(in: .whitespacesAndNewlines)
        passwordErrorMessage = passwordValid ? nil : "Password must be at least 8 characters long"
    }
    
    @MainActor
    func changePassword() async {
        validatePassword()
        
        if !passwordValid {
            return
        }
        
        isLoading = true
        
        defer {
            isLoading = false
        }
        
        let changePasswordRequest = ChangePasswordRequest(password: password)
        
        do {
            try await networkManager.patch(
                to: .changePassword,
                with: changePasswordRequest
            )
            
            isAlertPresented = true
            message = "Password successfully changed"
        } catch {
            isAlertPresented = true
            message = error.localizedDescription
        }
    }
}
