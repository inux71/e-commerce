//
//  Page.swift
//  e-commerce
//
//  Created by Kacper Grabiec on 25/11/2025.
//

import Foundation

struct Page<T: Decodable>: Decodable {
    let content: [T]
    let page: PageInfo
    
    struct PageInfo: Decodable {
        let size: Int
        let number: Int
        let totalElements: Int
        let totalPages: Int
        
        private init(size: Int, number: Int, totalElements: Int, totalPages: Int) {
            self.size = size
            self.number = number
            self.totalElements = totalElements
            self.totalPages = totalPages
        }
        
        init(from decoder: Decoder) throws {
            let container = try decoder.container(keyedBy: CodingKeys.self)
            self.size = try container.decode(Int.self, forKey: .size)
            self.number = try container.decode(Int.self, forKey: .number)
            self.totalElements = try container.decode(Int.self, forKey: .totalElements)
            self.totalPages = try container.decode(Int.self, forKey: .totalPages)
        }
        
        enum CodingKeys: CodingKey {
            case size
            case number
            case totalElements
            case totalPages
        }
    }
}
