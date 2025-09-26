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
                
                TextField(
                    text: $viewModel.lastName,
                    prompt: Text("last name")
                ) {
                    Text("Last name")
                }
                
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
                // viewModel.signUp
                
                coordinator.navigateBack()
            }) {
                Text("Sign up")
            }
            .buttonStyle(.glass)
            
            HStack {
                Text("Already have an acount?")
                
                Button(action: {
                    coordinator.navigateBack()
                }) {
                    Text("Sign in")
                }
            }
        }
        .padding(.horizontal)
    }
}

#Preview {
    RegisterView()
        .environmentObject(LoginCoordinator())
}
