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
            item: $coordinator.fullScreenCoverableItem,
            onDismiss: coordinator.onFullScreenCoverableItemDismiss
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
        .sheet(
            item: $coordinator.sheetableItem,
            onDismiss: coordinator.onSheetableItemDismiss,
        ) { item in
            switch item {
            case .addAddress:
                AddAddressView()
                    .environmentObject(coordinator)
            }
        }
        .task {
            if viewModel.isSignedIn() {
                await viewModel.refreshToken(onUnauthorized: {
                    coordinator.showFullScreenCoverableItem(item: .login)
                })
            } else {
                coordinator.showFullScreenCoverableItem(item: .login)
            }
        }
    }
}

#Preview {
    HomeView()
        .environmentObject(HomeCoordinator())
}
