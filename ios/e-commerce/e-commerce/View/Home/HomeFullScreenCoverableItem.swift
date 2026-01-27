//
//  HomeFullScreenCoverableItem.swift
//  e-commerce
//
//  Created by Kacper Grabiec on 26/09/2025.
//

import Foundation

enum HomeFullScreenCoverableItem: String, Identifiable {
    case login
    case settings
    
    var id: String {
        rawValue
    }
}
