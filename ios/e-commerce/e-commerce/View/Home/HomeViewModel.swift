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
    
    @Published var selectedTab: HomeTabViewItem = .products
    
    func isSignedIn() -> Bool {
        (try? keychainStorageManager.search(
            for: KeychainKey.accessToken,
            as: String.self
        )) != nil
    }
}
