//
//  AddressesView.swift
//  e-commerce
//
//  Created by Kacper Grabiec on 30/12/2025.
//

import SwiftUI

struct AddressesView: View {
    @StateObject private var viewModel: AddressesViewModel = AddressesViewModel()
    
    var body: some View {
        List(viewModel.addresses) { address in
            AddressCard(
                country: address.country,
                postalCode: address.postalCode,
                city: address.city,
                street: address.street
            )
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
        .overlay(alignment: .center) {
            if viewModel.isLoading {
                ProgressView()
            }
        }
        .task {
            await viewModel.getAddresses()
        }
    }
}

#Preview {
    NavigationStack {
        AddressesView()
    }
}
