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
                HomeTabViewItem.account.rawValue,
                systemImage: "person.crop.circle.fill",
                value: .account
            ) {
                AccountView()
                    .environmentObject(coordinator)
            }
        }
        .onAppear {
            if !viewModel.isSignedIn() {
                coordinator.show(item: .login)
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
    }
}

#Preview {
    HomeView()
}
