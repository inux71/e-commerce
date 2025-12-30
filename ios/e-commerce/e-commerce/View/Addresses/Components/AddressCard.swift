//
//  AddressCard.swift
//  e-commerce
//
//  Created by Kacper Grabiec on 30/12/2025.
//

import SwiftUI

struct AddressCard: View {
    let country: String
    let postalCode: String
    let city: String
    let street: String
    
    var body: some View {
        HStack {
            Image(systemName: "mappin")
            
            VStack(alignment: .leading) {
                Text(country)
                Text("\(postalCode) \(city)")
                Text(street)
            }
        }
    }
}

#Preview {
    AddressCard(
        country: "Polska",
        postalCode: "44-100",
        city: "Gliwice",
        street: "ul. Jakakolwiek 10"
    )
}
