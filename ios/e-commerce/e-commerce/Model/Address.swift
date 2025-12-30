//
//  Address.swift
//  e-commerce
//
//  Created by Kacper Grabiec on 30/12/2025.
//

import Foundation

struct Address: Decodable, Identifiable {
    let id: Int
    let country: String
    let postalCode: String
    let city: String
    let street: String
}
