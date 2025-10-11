//
//  LoginView.swift
//  e-commerce
//
//  Created by Kacper Grabiec on 26/09/2025.
//

import SwiftUI

struct LoginView: View {
    @EnvironmentObject private var homeCoordinator: HomeCoordinator
    
    @StateObject private var coordinator: LoginCoordinator = LoginCoordinator()
    @StateObject private var viewModel: LoginViewModel = LoginViewModel()
    
    var body: some View {
        NavigationStack(path: $coordinator.path) {
            VStack {
                Text("Sign in to your account")
                    .font(.title)
                
                Group {
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
                
                Button("Sign in") {
                    Task {
                        await viewModel.signIn()
                    }
                }
                .buttonStyle(.glass)
                .disabled(viewModel.signInButtonDisabled)
                
                HStack {
                    Text("Don't have an acount?")
                    
                    Button("Sign up") {
                        coordinator.navigate(to: .register)
                    }
                }
            }
            .navigationDestination(for: LoginDestination.self) { destination in
                switch destination {
                case .register:
                    RegisterView()
                        .environmentObject(coordinator)
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
            .onChange(of: viewModel.isSignedIn) {
                homeCoordinator.dismiss()
            }
            .overlay(alignment: .center) {
                if viewModel.isLoading {
                    ProgressView()
                }
            }
            .navigationTitle(Text("Sign in"))
            .padding(.horizontal)
        }
    }
}

#Preview {
    LoginView()
        .environmentObject(HomeCoordinator())
}
