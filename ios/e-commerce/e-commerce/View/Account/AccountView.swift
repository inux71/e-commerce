//
//  AccountView.swift
//  e-commerce
//
//  Created by Kacper Grabiec on 20/11/2025.
//

import SwiftUI

struct AccountView: View {
    @EnvironmentObject private var homeCoordinator: HomeCoordinator
    
    @StateObject private var coordinator: AccountCoordinator = AccountCoordinator()
    @StateObject private var viewModel: AccountViewModel = AccountViewModel()
    
    var body: some View {
        NavigationStack {
            List {
                NavigationLink(destination: CredentialsView(email: viewModel.email).environmentObject(homeCoordinator)) {
                    Label("Credentials", systemImage: "key.shield.fill")
                }
                
                Section {
                    Button(
                        "Sign out",
                        systemImage: "rectangle.portrait.and.arrow.right",
                        role: .destructive
                    ) {
                        viewModel.signOut()
                        homeCoordinator.show(item: .login)
                    }
                    .foregroundStyle(Color(.systemRed))
                }
            }
            .navigationTitle("Account")
            .alert(
                viewModel.errorMessage ?? "",
                isPresented: $viewModel.isAlertPresented
            ) {
                Button("OK") {
                    viewModel.isAlertPresented = false
                    viewModel.errorMessage = nil
                }
            }
            .onAppear {
                Task {
                    await viewModel.getCustomer(onUnauthorized: {
                        homeCoordinator.show(
                            item: .login,
                            onDismiss: {
                                Task {
                                    await viewModel.getCustomer()
                                }
                            }
                        )
                    })
                }
            }
            .overlay(alignment: .center) {
                if viewModel.isLoading {
                    ProgressView()
                }
            }
        }
    }
}

#Preview {
    NavigationStack {
        AccountView()
            .environmentObject(HomeCoordinator())
    }
}
