//
//  HomeView.swift
//  e-commerce
//
//  Created by Kacper Grabiec on 26/09/2025.
//

import SwiftUI

struct HomeView: View {
    @StateObject private var coordinator: HomeCoordinator = HomeCoordinator()
    @StateObject private var viewModel: HomeViewModel = HomeViewModel()
    
    var body: some View {
        TabView(selection: $viewModel.selectedTab) {
            Tab(
                HomeTabViewItem.products.rawValue,
                systemImage: "list.bullet",
                value: .products
            ) {
                ProductsView()
            }
            
            Tab(
                HomeTabViewItem.cart.rawValue,
                systemImage: "cart",
                value: .cart
            ) {
                CartView()
            }
            
            Tab(
                HomeTabViewItem.account.rawValue,
                systemImage: "person.crop.circle.fill",
                value: .account
            ) {
                AccountView()
                    .environmentObject(coordinator)
            }
        }
        .fullScreenCover(
            item: $coordinator.item,
            onDismiss: coordinator.onDismiss
        ) { item in
            switch item {
            case .login:
                LoginView()
                    .environmentObject(coordinator)
            }
        }
        .overlay(alignment: .center) {
            if viewModel.isLoading {
                ProgressView()
            }
        }
        .task {
            if viewModel.isSignedIn() {
                await viewModel.refreshToken(onUnauthorized: {
                    coordinator.show(item: .login)
                })
            } else {
                coordinator.show(item: .login)
            }
        }
    }
}

#Preview {
    HomeView()
        .environmentObject(HomeCoordinator())
}
