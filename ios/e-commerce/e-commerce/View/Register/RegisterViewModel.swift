//
//  RegisterViewModel.swift
//  e-commerce
//
//  Created by Kacper Grabiec on 26/09/2025.
//

import Combine
import Foundation

class RegisterViewModel: ObservableObject {
    private let networkManager: NetworkManager = NetworkManager.shared
    
    @Published var isLoading: Bool = false
    @Published var isAccountCreated: Bool = false
    @Published var isAlertPresented: Bool = false
    @Published var errorMessage: String? = nil

    @Published var firstName: String = ""
    @Published var firstNameErrorMessage: String? = nil
    @Published var lastName: String = ""
    @Published var lastNameErrorMessage: String? = nil
    @Published var email: String = ""
    @Published var emailErrorMessage: String? = nil
    @Published var password: String = ""
    @Published var passwordErrorMessage: String? = nil
    
    private var firstNameValid: Bool {
        !firstName.isEmpty
    }
    
    private var lastNameValid: Bool {
        !lastName.isEmpty
    }
    
    private var emailValid: Bool {
        email.range(
            of: String.emailPattern,
            options: .regularExpression
        ) != nil
    }
    
    private var passwordValid: Bool {
        password.trimmingCharacters(in: .whitespacesAndNewlines).count >= 8
    }
    
    var signUpButtonDisabled: Bool {
        !firstNameValid || !lastNameValid || !emailValid || !passwordValid
    }
    
    func validateFirstName() {
        firstName = firstName.trimmingCharacters(in: .whitespacesAndNewlines)
        firstNameErrorMessage = firstNameValid ? nil : "First name is required"
    }
    
    func validateLastName() {
        lastName = lastName.trimmingCharacters(in: .whitespacesAndNewlines)
        lastNameErrorMessage = lastNameValid ? nil : "Last name is required"
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
    func signUp() async {
        isLoading = true
        
        defer {
            isLoading = false
        }
        
        let registerRequest = RegisterRequest(
            email: email,
            password: password,
            firstName: firstName,
            lastName: lastName
        )
        
        do {
            try await networkManager.post(
                to: .register,
                with: registerRequest
            )
            
            isAccountCreated = true
        } catch {
            isAlertPresented = true
            errorMessage = error.localizedDescription
        }
    }
}
