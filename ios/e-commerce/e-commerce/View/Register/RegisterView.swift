//
//  RegisterView.swift
//  e-commerce
//
//  Created by Kacper Grabiec on 26/09/2025.
//

import SwiftUI

struct RegisterView: View {
    @EnvironmentObject private var coordinator: LoginCoordinator
    
    @StateObject private var viewModel: RegisterViewModel = RegisterViewModel()
    
    var body: some View {
        VStack {
            Text("Create an account")
                .font(.title)
            
            Group {
                TextField(
                    text: $viewModel.firstName,
                    prompt: Text("first name")
                ) {
                    Text("First name")
                }
                .onSubmit {
                    viewModel.validateFirstName()
                }
                
                if let firstNameErrorMessage = viewModel.firstNameErrorMessage {
                    Text(firstNameErrorMessage)
                        .foregroundStyle(.red)
                        .frame(
                            maxWidth: .infinity,
                            alignment: .leading
                        )
                }
                
                TextField(
                    text: $viewModel.lastName,
                    prompt: Text("last name")
                ) {
                    Text("Last name")
                }
                .onSubmit {
                    viewModel.validateLastName()
                }
                
                if let lastNameErrorMessage = viewModel.lastNameErrorMessage {
                    Text(lastNameErrorMessage)
                        .foregroundStyle(.red)
                        .frame(
                            maxWidth: .infinity,
                            alignment: .leading
                        )
                }
                
                TextField(
                    text: $viewModel.email,
                    prompt: Text("email")
                ) {
                    Text("Email")
                }
                .keyboardType(.emailAddress)
                .onSubmit {
                    viewModel.validateEmail()
                }
                
                if let emailErrorMessage = viewModel.emailErrorMessage {
                    Text(emailErrorMessage)
                        .foregroundStyle(.red)
                        .frame(
                            maxWidth: .infinity,
                            alignment: .leading
                        )
                }
                
                SecureField(
                    text: $viewModel.password,
                    prompt: Text("password")
                ) {
                    Text("Password")
                }
                .onSubmit {
                    viewModel.validatePassword()
                }
                
                if let passwordErrorMessage = viewModel.passwordErrorMessage {
                    Text(passwordErrorMessage)
                        .foregroundStyle(.red)
                        .frame(
                            maxWidth: .infinity,
                            alignment: .leading
                        )
                }
            }
            .textFieldStyle(.roundedBorder)
            
            Button("Sign up") {
                Task {
                    await viewModel.signUp()
                }
            }
            .buttonStyle(.glass)
            .disabled(viewModel.signUpButtonDisabled)
            
            HStack {
                Text("Already have an acount?")
                
                Button("Sign in") {
                    coordinator.navigateBack()
                }
            }
        }
        .alert(
            viewModel.errorMessage ?? "",
            isPresented: $viewModel.isAlertPresented
        ) {
            Button("OK") {
                viewModel.isAlertPresented = false
                viewModel.errorMessage = nil
            }
        }
        .onChange(of: viewModel.isAccountCreated) {
            coordinator.navigateBack()
        }
        .overlay(alignment: .center) {
            if viewModel.isLoading {
                ProgressView()
            }
        }
        .navigationTitle(Text("Sign up"))
        .padding(.horizontal)
    }
}

#Preview {
    NavigationStack {
        RegisterView()
            .environmentObject(LoginCoordinator())
    }
}
