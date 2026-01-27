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
                Section {
                    NavigationLink(destination: CredentialsView(email: viewModel.email)) {
                        Label("Credentials", systemImage: "key.shield.fill")
                    }
                }
                
                Section("Orders") {
                    NavigationLink(destination: AddressesView().environmentObject(homeCoordinator)) {
                        LabeledContent {
                            Text("\(viewModel.addresses)")
                        } label: {
                            Label("Addresses", systemImage: "location.fill")
                        }
                    }
                }
                
                Section {
                    Button(
                        "Sign out",
                        systemImage: "rectangle.portrait.and.arrow.right",
                        role: .destructive
                    ) {
                        viewModel.signOut()
                        homeCoordinator.showFullScreenCoverableItem(item: .login)
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
            .overlay(alignment: .center) {
                if viewModel.isLoading {
                    ProgressView()
                }
            }
            .task {
                await viewModel.getCustomer()
            }
            .toolbar {
                Button("Settings", systemImage: "gear") {
                    homeCoordinator.showFullScreenCoverableItem(item: .settings)
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
