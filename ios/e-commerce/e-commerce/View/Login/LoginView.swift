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
                    
                    SecureField(
                        text: $viewModel.password,
                        prompt: Text("password")
                    ) {
                        Text("Password")
                    }
                }
                .textFieldStyle(.roundedBorder)
                
                Button(action: {
                    // viewModel.signIn
                    
                    homeCoordinator.dismiss()
                }) {
                    Text("Sign in")
                }
                .buttonStyle(.glass)
                
                HStack {
                    Text("Don't have an acount?")
                    
                    Button(action: {
                        coordinator.navigate(to: .register)
                    }) {
                        Text("Sign up")
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
            .padding(.horizontal)
        }
    }
}

#Preview {
    LoginView()
        .environmentObject(HomeCoordinator())
}
