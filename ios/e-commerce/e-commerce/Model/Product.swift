//
//  Product.swift
//  e-commerce
//
//  Created by Kacper Grabiec on 25/11/2025.
//

import Foundation

struct Product: Decodable, Identifiable {
    let id: Int
    let name: String
    let description: String
    let price: Double
}
