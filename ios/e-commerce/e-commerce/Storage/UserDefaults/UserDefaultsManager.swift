//
//  UserDefaultsManager.swift
//  e-commerce
//
//  Created by Kacper Grabiec on 27/01/2026.
//

import Foundation

class UserDefaultsManager {
    static let shared = UserDefaultsManager()
    
    private init() {}
    
    func bool(for key: UserDefaultsKey) -> Bool {
        UserDefaults.standard.bool(forKey: key.rawValue)
    }
    
    func save<T: Encodable>(for key: UserDefaultsKey, value: T) {
        UserDefaults.standard.set(value, forKey: key.rawValue)
    }
    
    func delete(for key: UserDefaultsKey) {
        UserDefaults.standard.removeObject(forKey: key.rawValue)
    }
}
