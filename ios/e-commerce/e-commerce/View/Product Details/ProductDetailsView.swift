//
//  ProductDetailsView.swift
//  e-commerce
//
//  Created by Kacper Grabiec on 25/11/2025.
//

import SwiftUI

struct ProductDetailsView: View {
    @StateObject private var viewModel: ProductDetailsViewModel = ProductDetailsViewModel()
    
    let id: Int
    let name: String
    let description: String
    let price: Double
    
    var body: some View {
        List {
            Image(systemName: "photo")
                .resizable()
                .aspectRatio(contentMode: .fit)
            Text(description)
            LabeledContent(
                "Price",
                value: price.formatted(.currency(code: "PLN"))
            )
            
            Section {
                Button(
                    "Add to cart",
                    systemImage: "cart.badge.plus"
                ) {
                    // TODO: add to cart
                }
            }
        }
        .navigationTitle(name)
    }
}

#Preview {
    NavigationStack {
        ProductDetailsView(
            id: 1,
            name: "iPhone 14",
            description: "description",
            price: 1234.67
        )
    }
}
