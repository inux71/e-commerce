//
//  KeyChainManager.swift
//  e-commerce
//
//  Created by Kacper Grabiec on 11/10/2025.
//

import Foundation

class KeychainStorageManager {
    static let shared = KeychainStorageManager()
    
    private init() {}
    
    func search<T: Decodable>(for key: KeychainKey, as type: T.Type) throws -> T {
        let query: [String: Any] = [
            kSecClass as String: kSecClassGenericPassword,
            kSecAttrAccount as String: key.rawValue,
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
    
    func save<T: Encodable>(for key: KeychainKey, value: T) throws {
        let data = try JSONEncoder().encode(value)
        
        let query: [String: Any] = [
            kSecClass as String: kSecClassGenericPassword,
            kSecAttrAccount as String: key.rawValue
        ]
        
        let attributesToUpdate: [String: Any] = [
            kSecValueData as String: data
        ]
        
        let status: OSStatus = SecItemUpdate(query as CFDictionary, attributesToUpdate as CFDictionary)
        
        switch status {
        case errSecSuccess:
            return
        case errSecItemNotFound:
            let addQuery: [String: Any] = [
                kSecClass as String: kSecClassGenericPassword,
                kSecAttrAccount as String: key.rawValue,
                kSecValueData as String: data
            ]
            
            let addStatus = SecItemAdd(addQuery as CFDictionary, nil)
            
            guard addStatus == errSecSuccess else {
                throw KeychainError.unhandledError(status: addStatus)
            }
        default:
            throw KeychainError.unhandledError(status: status)
        }
    }
    
    func delete(for key: KeychainKey) throws {
        let query: [String: Any] = [
            kSecClass as String: kSecClassGenericPassword,
            kSecAttrAccount as String: key.rawValue
        ]
        
        let status: OSStatus = SecItemDelete(query as CFDictionary)
        
        guard status == errSecSuccess || status == errSecItemNotFound else {
            throw KeychainError.unhandledError(status: status)
        }
    }
}
