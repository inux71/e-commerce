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
        NavigationStack {
            Button(action: {
                coordinator.show(item: .login)
            }) {
                Text("Show login full screen cover")
            } // add proper view here later
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
}

#Preview {
    HomeView()
}
