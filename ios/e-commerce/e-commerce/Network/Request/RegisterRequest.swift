//
//  RegisterRequest.swift
//  e-commerce
//
//  Created by Kacper Grabiec on 10/10/2025.
//

import Foundation

struct RegisterRequest: Encodable {
    let email: String
    let password: String
    let firstName: String
    let lastName: String
}
