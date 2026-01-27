//
//  SettingsViewModel.swift
//  e-commerce
//
//  Created by Kacper Grabiec on 27/01/2026.
//

import Combine
import Foundation

class SettingsViewModel: ObservableObject {
    private let userDefaultsManager: UserDefaultsManager = UserDefaultsManager.shared
    
    @Published var isDarkModeEnabled: Bool = false
    
    func changeMode() {
        userDefaultsManager.save(for: UserDefaultsKey.darkMode, value: isDarkModeEnabled)
    }
    
    func getMode() {
        isDarkModeEnabled = userDefaultsManager.bool(for: UserDefaultsKey.darkMode)
    }
}
