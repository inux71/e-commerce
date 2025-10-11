//
//  Storage.swift
//  e-commerce
//
//  Created by Kacper Grabiec on 11/10/2025.
//

import Foundation

protocol StorageManager {
    func save<T: Encodable>(for key: String, value: T) throws
}
