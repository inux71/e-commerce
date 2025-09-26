//
//  FullScreenCoverable.swift
//  e-commerce
//
//  Created by Kacper Grabiec on 26/09/2025.
//

import Foundation

protocol FullScreenCoverable {
    associatedtype Item: Identifiable
    
    var item: Item? { get }
    var onDismiss: (() -> Void)? { get }
    
    func show(item: Item, onDismiss: (() -> Void)?)
    func dismiss()
}
