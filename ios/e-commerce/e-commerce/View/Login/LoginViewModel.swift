//
//  LoginViewModel.swift
//  e-commerce
//
//  Created by Kacper Grabiec on 26/09/2025.
//

import Combine
import Foundation

class LoginViewModel: ObservableObject {
    private let keychainStorageManager = KeychainStorageManager.shared
    private let networkManager: NetworkManager = NetworkManager.shared
    
    @Published var isLoading: Bool = false
    @Published var isSignedIn: Bool = false
    @Published var isAlertPresented: Bool = false
    @Published var errorMessage: String? = nil
    
    @Published var email: String = ""
    @Published var emailErrorMessage: String? = nil
    @Published var password: String = ""
    @Published var passwordErrorMessage: String? = nil
    
    private var emailValid: Bool {
        email.range(
            of: String.emailPattern,
            options: .regularExpression
        ) != nil
    }
    
    private var passwordValid: Bool {
        password.trimmingCharacters(in: .whitespacesAndNewlines).count >= 8
    }
    
    var signInButtonDisabled: Bool {
        !emailValid || !passwordValid
    }
    
    func validateEmail() {
        email = email.trimmingCharacters(in: .whitespacesAndNewlines)
        emailErrorMessage = emailValid ? nil : "Email is not valid"
    }
    
    func validatePassword() {
        password = password.trimmingCharacters(in: .whitespacesAndNewlines)
        passwordErrorMessage = passwordValid ? nil : "Password must be at least 8 characters long"
    }
    
    @MainActor
    func signIn() async {
        isLoading = true
        
        defer {
            isLoading = false
        }
        
        let loginRequest = LoginRequest(
            email: email,
            password: password
        )
        
        do {
            let token: Token = try await networkManager.post(
                to: .login,
                with: loginRequest
            )
            
            try keychainStorageManager.save(
                for: KeychainKey.accessToken,
                value: token.token
            )
            
            isSignedIn = true
        } catch {
            isAlertPresented = true
            errorMessage = error.localizedDescription
        }
    }
}
