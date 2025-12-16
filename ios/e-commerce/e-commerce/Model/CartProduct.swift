//
//  CartProduct.swift
//  e-commerce
//
//  Created by Kacper Grabiec on 02/12/2025.
//

import Foundation

struct CartProduct: Decodable, Identifiable {
    let id: Int
    let product: Product
    var quantity: Int
}
