//
//  e_commerceApp.swift
//  e-commerce
//
//  Created by Kacper Grabiec on 26/09/2025.
//

import SwiftUI

@main
struct e_commerceApp: App {
    @AppStorage(UserDefaultsKey.darkMode.rawValue) private var darkMode: Bool = false
    
    var body: some Scene {
        WindowGroup {
            HomeView()
                .preferredColorScheme(darkMode ? .dark : .light)
        }
    }
}
