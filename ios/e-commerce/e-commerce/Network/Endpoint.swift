//
//  Endpoint.swift
//  e-commerce
//
//  Created by Kacper Grabiec on 10/10/2025.
//

import Foundation

enum Endpoint {
    case register
    case login
    case refresh
    case products
    case cart
    case addToCart(productId: Int)
    case removeFromCart(productId: Int)
    case me
    case changePassword
    
    var path: String {
        switch self {
        case .register: 
            return "app/customer"
        case .login: 
            return "app/auth/login"
        case .refresh: 
            return "app/auth/refresh"
        case .products: 
            return "/app/product"
        case .cart:
            return "app/cart"
        case .addToCart(let productId):
            return "app/cart/\(productId)"
        case .removeFromCart(let productId):
            return "app/cart/\(productId)"
        case .me:
            return "app/customer/me"
        case .changePassword:
            return "app/customer/change-password"
        }
    }
    
    var requiresAuth: Bool {
        switch self {
        case .register, .login, .products:
            return false
        default:
            return true
        }
    }
}
