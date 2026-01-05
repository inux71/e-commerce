//
//  FullScreenCoverable.swift
//  e-commerce
//
//  Created by Kacper Grabiec on 26/09/2025.
//

import Foundation

protocol FullScreenCoverable {
    associatedtype FullScreenCoverableItem: Identifiable
    
    var fullScreenCoverableItem: FullScreenCoverableItem? { get }
    var onFullScreenCoverableItemDismiss: (() -> Void)? { get }
    
    func showFullScreenCoverableItem(item: FullScreenCoverableItem, onDismiss: (() -> Void)?)
    func dismissFullScreenCoverableItem()
}
