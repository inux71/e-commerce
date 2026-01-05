//
//  Sheetable.swift
//  e-commerce
//
//  Created by Kacper Grabiec on 02/01/2026.
//

import Foundation

protocol Sheetable {
    associatedtype SheetableItem: Identifiable
    
    var sheetableItem: SheetableItem? { get }
    var onSheetableItemDismiss: (() -> Void)? { get }
    
    func showSheetableItem(item: SheetableItem, onDismiss: (() -> Void)?)
    func dismissSheetableItem()
}
