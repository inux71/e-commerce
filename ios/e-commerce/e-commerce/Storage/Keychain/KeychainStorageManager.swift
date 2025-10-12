//
//  KeyChainManager.swift
//  e-commerce
//
//  Created by Kacper Grabiec on 11/10/2025.
//

import Foundation

class KeychainStorageManager: StorageManager {
    static let shared = KeychainStorageManager()
    
    private init() {}
    
    func search<T: Decodable>(for key: String, as type: T.Type) throws -> T {
        let query: [String: Any] = [
            kSecClass as String: kSecClassGenericPassword,
            kSecAttrAccount as String: key,
            kSecReturnData as String: true,
            kSecMatchLimit as String: kSecMatchLimitOne
        ]
        
        var item: CFTypeRef?
        let status: OSStatus = SecItemCopyMatching(query as CFDictionary, &item)
        
        guard status != errSecItemNotFound else {
            throw KeychainError.noPassword
        }
        
        guard status == errSecSuccess else {
            throw KeychainError.unhandledError(status: status)
        }
        
        guard let data = item as? Data else {
            throw KeychainError.unexpectedPasswordData
        }
        
        return try JSONDecoder().decode(T.self, from: data)
    }
    
    func save<T: Encodable>(for key: String, value: T) throws {
        let data = try JSONEncoder().encode(value)
        
        let query: [String: Any] = [
            kSecClass as String: kSecClassGenericPassword,
            kSecAttrAccount as String: key,
            kSecValueData as String: data,
        ]
        
        let status: OSStatus = SecItemAdd(query as CFDictionary, nil)
        
        guard status == errSecSuccess else {
            throw KeychainError.unhandledError(status: status)
        }
    }
}
