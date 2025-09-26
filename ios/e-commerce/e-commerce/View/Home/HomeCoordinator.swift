//
//  HomeCoordinator.swift
//  e-commerce
//
//  Created by Kacper Grabiec on 26/09/2025.
//

import Combine
import Foundation

class HomeCoordinator: ObservableObject, FullScreenCoverable {
    typealias Item = HomeFullScreenCoverableItem
    
    @Published var item: HomeFullScreenCoverableItem?
    private(set) var onDismiss: (() -> Void)?
    
    func show(item: HomeFullScreenCoverableItem, onDismiss: (() -> Void)? = nil) {
        self.item = item
        self.onDismiss = onDismiss
    }
    
    func dismiss() {
        self.item = nil
    }
}
