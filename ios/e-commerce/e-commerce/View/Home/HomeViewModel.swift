//
//  HomeViewModel.swift
//  e-commerce
//
//  Created by Kacper Grabiec on 26/09/2025.
//

import Combine
import Foundation

class HomeViewModel: ObservableObject {
    private let keychainStorageManager: StorageManager = KeychainStorageManager.shared
    private let networkManager: NetworkManager = NetworkManager.shared
    
    @Published var isLoading: Bool = false
    
    @Published var selectedTab: HomeTabViewItem = .products
    
    func isSignedIn() -> Bool {
        (try? keychainStorageManager.search(
            for: KeychainKey.accessToken,
            as: String.self
        )) != nil
    }
    
    @MainActor
    func refreshToken(onUnauthorized: () -> Void) async {
        isLoading = true
        
        defer {
            isLoading = false
        }
        
        do {
            let token: Token = try await networkManager.post(to: .refresh)
            
            try keychainStorageManager.save(
                for: KeychainKey.accessToken,
                value: token.token
            )
        } catch {
            onUnauthorized()
        }
    }
}
