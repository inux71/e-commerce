//
//  SettingsView.swift
//  e-commerce
//
//  Created by Kacper Grabiec on 27/01/2026.
//

import SwiftUI

struct SettingsView: View {
    @EnvironmentObject private var homeCoordinator: HomeCoordinator
    
    @StateObject private var viewModel: SettingsViewModel = SettingsViewModel()
    
    var body: some View {
        NavigationStack {
            List {
                Section("Appearance") {
                    Toggle("Dark Mode", isOn: $viewModel.isDarkModeEnabled)
                }
            }
            .navigationTitle("Settings")
            .onAppear {
                viewModel.getMode()
            }
            .onChange(of: viewModel.isDarkModeEnabled) {
                viewModel.changeMode()
            }
            .toolbar {
                Button("Close", systemImage: "xmark") {
                    homeCoordinator.dismissFullScreenCoverableItem()
                }
            }
        }
    }
}

#Preview {
    NavigationStack {
        SettingsView()
            .environmentObject(HomeCoordinator())
    }
}
