//
//  HomeCoordinator.swift
//  e-commerce
//
//  Created by Kacper Grabiec on 26/09/2025.
//

import Combine
import Foundation

class HomeCoordinator: ObservableObject, FullScreenCoverable, Sheetable {
    typealias FullScreenCoverableItem = HomeFullScreenCoverableItem
    typealias SheetableItem = HomeSheetableItem
    
    @Published var fullScreenCoverableItem: HomeFullScreenCoverableItem?
    private(set) var onFullScreenCoverableItemDismiss: (() -> Void)?
    
    @Published var sheetableItem: HomeSheetableItem?
    private(set) var onSheetableItemDismiss: (() -> Void)?
    
    func showFullScreenCoverableItem(item: HomeFullScreenCoverableItem, onDismiss: (() -> Void)? = nil) {
        self.fullScreenCoverableItem = item
        self.onFullScreenCoverableItemDismiss = onDismiss
    }
    
    func dismissFullScreenCoverableItem() {
        self.fullScreenCoverableItem = nil
    }
    
    func showSheetableItem(item: HomeSheetableItem, onDismiss: (() -> Void)? = nil) {
        self.sheetableItem = item
        self.onSheetableItemDismiss = onDismiss
    }
    
    func dismissSheetableItem() {
        self.sheetableItem = nil
    }
}
