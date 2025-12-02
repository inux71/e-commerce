//
//  CartItem.swift
//  e-commerce
//
//  Created by Kacper Grabiec on 02/12/2025.
//

import SwiftUI

struct CartItem: View {
    @State private var quantity: Int
    
    let name: String
    let price: Double
    
    init(
        name: String,
        quantity: Int,
        price: Double
    ) {
        self.name = name
        self.quantity = quantity
        self.price = price
    }
    
    var body: some View {
        HStack {
            Image(systemName: "photo")
                .resizable()
                .aspectRatio(contentMode: .fit)
            
            VStack(alignment: .leading) {
                Text(name)
                
                Stepper(
                    "Quantity: \(quantity)",
                    value: $quantity,
                    in: 1...Int.max
                )
                
                Text(price.formatted(.currency(code: "PLN")))
            }
        }
    }
}

#Preview {
    CartItem(
        name: "iPhone 13",
        quantity: 1,
        price: 125.46
    )
}
