//
//  AddressesViewModel.swift
//  e-commerce
//
//  Created by Kacper Grabiec on 30/12/2025.
//

import Combine
import Foundation

class AddressesViewModel: ObservableObject {
    private let networkManager: NetworkManager = NetworkManager.shared
    
    @Published var isLoading: Bool = false
    @Published var isAlertPresented: Bool = false
    @Published var errorMessage: String? = nil
    
    @Published var addresses: [Address] = []
    
    @MainActor
    func getAddresses() async {
        isLoading = true
        
        defer {
            isLoading = false
        }
        
        do {
            let addresses: [Address] = try await networkManager.get(from: .addresses)
            
            self.addresses = addresses
        } catch {
            isAlertPresented = true
            errorMessage = error.localizedDescription
        }
    }
    
    @MainActor
    func removeAddress(at offsets: IndexSet) {
        guard let index: Int = offsets.first else { return }
        
        let addressId: Int = addresses[index].id
        
        Task {
            isLoading = true
            
            defer {
                isLoading = false
            }
            
            do {
                try await networkManager.delete(at: .removeAddress(addressId: addressId))
                
                addresses.remove(at: index)
            } catch {
                isAlertPresented = true
                errorMessage = error.localizedDescription
            }
        }
    }
}
