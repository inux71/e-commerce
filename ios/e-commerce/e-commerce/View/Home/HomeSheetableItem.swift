//
//  HomeSheetableItem.swift
//  e-commerce
//
//  Created by Kacper Grabiec on 02/01/2026.
//

import Foundation

enum HomeSheetableItem: String, Identifiable {
    case addAddress
    
    var id: String {
        rawValue
    }
}
