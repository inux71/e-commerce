//
//  KeyChainError.swift
//  e-commerce
//
//  Created by Kacper Grabiec on 11/10/2025.
//

import Foundation

enum KeychainError: Error {
    case unhandledError(status: OSStatus)
}
