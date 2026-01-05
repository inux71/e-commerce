//
//  Country.swift
//  e-commerce
//
//  Created by Kacper Grabiec on 02/01/2026.
//

import Foundation

struct Country: Decodable, Identifiable, Hashable {
    let id: Int
    let name: String
    let iso2: String
}
