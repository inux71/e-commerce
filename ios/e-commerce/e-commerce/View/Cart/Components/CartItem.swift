//
//  CartItem.swift
//  e-commerce
//
//  Created by Kacper Grabiec on 02/12/2025.
//

import SwiftUI

struct CartItem: View {
    @Binding var quantity: Int
    
    let name: String
    let price: Double
    
    var totalPrice: Double {
        Double(quantity) * price
    }
    
    var body: some View {
        HStack {
            Image(systemName: "photo")
                .resizable()
                .aspectRatio(contentMode: .fit)
            
            VStack(alignment: .leading) {
                Text(name)
                
                Stepper(
                    "Q: \(quantity)",
                    value: $quantity,
                    in: 1...Int.max
                )
                
                Text(totalPrice.formatted(.currency(code: "PLN")))
            }
        }
    }
}

#Preview {
    CartItem(
        quantity: Binding.constant(1),
        name: "iPhone 13",
        price: 5799
    )
}
