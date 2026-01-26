//
//  AddressesView.swift
//  e-commerce
//
//  Created by Kacper Grabiec on 30/12/2025.
//

import SwiftUI

struct AddressesView: View {
    @EnvironmentObject private var homeCoordinator: HomeCoordinator
    
    @StateObject private var viewModel: AddressesViewModel = AddressesViewModel()
    
    private func addAddress() {
        homeCoordinator.showSheetableItem(
            item: .addAddress,
            onDismiss: {
                Task {
                    await viewModel.getAddresses()
                }
            }
        )
    }
    
    var body: some View {
        List {
            ForEach($viewModel.addresses) { $address in
                AddressCard(
                    country: address.country,
                    postalCode: address.postalCode,
                    city: address.city,
                    street: address.street
                )
            }
            .onDelete(perform: viewModel.removeAddress)
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
        .navigationTitle("Addresses")
        .overlay {
            if viewModel.addresses.isEmpty {
                ContentUnavailableView {
                    Label("No addresses", systemImage: "mappin.slash")
                } description: {
                    Text("You don't have any addresses.")
                } actions: {
                    Button("Add address", systemImage: "plus") {
                        addAddress()
                    }
                }
            }
        }
        .overlay(alignment: .center) {
            if viewModel.isLoading {
                ProgressView()
            }
        }
        .task {
            await viewModel.getAddresses()
        }
        .toolbar {
            Button("Add address", systemImage: "plus") {
                addAddress()
            }
        }
    }
}

#Preview {
    NavigationStack {
        AddressesView()
            .environmentObject(HomeCoordinator())
    }
}
