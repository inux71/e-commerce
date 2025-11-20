//
//  Storage.swift
//  e-commerce
//
//  Created by Kacper Grabiec on 11/10/2025.
//

import Foundation

protocol StorageManager {
    func search<T: Decodable>(for key: String, as type: T.Type) throws -> T
    func save<T: Encodable>(for key: String, value: T) throws
    func delete(for key: String) throws
}
