//
//  ProductsView.swift
//  e-commerce
//
//  Created by Kacper Grabiec on 25/11/2025.
//

import SwiftUI

struct ProductsView: View {
    @EnvironmentObject private var homeCoordinator: HomeCoordinator
    
    @StateObject private var viewModel: ProductsViewModel = ProductsViewModel()
    
    var body: some View {
        NavigationStack {
            ScrollView {
                LazyVGrid(columns: [GridItem(), GridItem()]) {
                    ForEach(viewModel.products) { product in
                        ProductCard(
                            name: product.name,
                            price: product.price
                        )
                    }
                }
                
                if viewModel.shouldLoadMore {
                    Button("Load more") {
                        Task {
                            await viewModel.getProducts()
                        }
                    }
                }
            }
            .alert(
                viewModel.errorMessage ?? "",
                isPresented: $viewModel.isAlertPresented
            ) {
                Button("OK") {
                    viewModel.isAlertPresented = false
                    viewModel.errorMessage = nil
                }
            }
            .navigationTitle("Products")
            .overlay(alignment: .center) {
                if viewModel.isLoading {
                    ProgressView()
                }
            }
            .padding(.horizontal)
            .task {
                await viewModel.getProducts()
            }
        }
    }
}

#Preview {
    NavigationStack {
        ProductsView()
            .environmentObject(HomeCoordinator())
    }
}
