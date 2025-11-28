//
//  Endpoint.swift
//  e-commerce
//
//  Created by Kacper Grabiec on 10/10/2025.
//

import Foundation

enum Endpoint: String {
    case register = "app/customer"
    case login = "app/auth/login"
    case refresh = "app/auth/refresh"
    case products = "/app/product"
    case me = "app/customer/me"
    case changePassword = "app/customer/change-password"
    
    var requiresAuth: Bool {
        switch self {
        case .register, .login, .products:
            return false
        default:
            return true
        }
    }
}
