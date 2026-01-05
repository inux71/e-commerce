//
//  CreateAddressRequest.swift
//  e-commerce
//
//  Created by Kacper Grabiec on 05/01/2026.
//

import Foundation

struct CreateAddressRequest: Encodable {
    let postalCode: String
    let cityId: Int
    let street: String
}
