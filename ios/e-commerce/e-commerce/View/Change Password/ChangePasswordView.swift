//
//  ChangePasswordView.swift
//  e-commerce
//
//  Created by Kacper Grabiec on 21/11/2025.
//

import SwiftUI

struct ChangePasswordView: View {
    @StateObject private var viewModel: ChangePasswordViewModel = ChangePasswordViewModel()
    
    var body: some View {
        List {
            SecureField(
                text: $viewModel.password,
                prompt: Text("password")
            ) {
                Text("Password")
            }
            .onSubmit {
                Task {
                    await viewModel.changePassword()
                }
            }
            .submitLabel(.done)
            
            if let passwordErrorMessage = viewModel.passwordErrorMessage {
                Text(passwordErrorMessage)
                    .foregroundStyle(.red)
                    .frame(
                        maxWidth: .infinity,
                        alignment: .leading
                    )
            }
        }
        .alert(
            viewModel.message ?? "",
            isPresented: $viewModel.isAlertPresented
        ) {
            Button("OK") {
                viewModel.isAlertPresented = false
                viewModel.message = nil
            }
        }
        .overlay(alignment: .center) {
            if viewModel.isLoading {
                ProgressView()
            }
        }
        .navigationTitle("Change Password")
    }
}

#Preview {
    NavigationStack {
        ChangePasswordView()
    }
}
