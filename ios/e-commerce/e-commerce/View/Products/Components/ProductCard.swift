//
//  ProductCard.swift
//  e-commerce
//
//  Created by Kacper Grabiec on 25/11/2025.
//

import SwiftUI

struct ProductCard: View {
    let name: String
    let price: Double
    
    var body: some View {
        VStack {
            Image(systemName: "photo")
                .resizable()
                .aspectRatio(contentMode: .fit)
            Text(name)
            Text(price.formatted(.currency(code: "PLN")))
        }
    }
}

#Preview {
    ProductCard(
        name: "iPhone 14 Pro Max",
        price: 579.99
    )
}
