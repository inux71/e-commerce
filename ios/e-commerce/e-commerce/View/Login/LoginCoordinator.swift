//
//  LoginCoordinator.swift
//  e-commerce
//
//  Created by Kacper Grabiec on 26/09/2025.
//

import Combine
import Foundation
import SwiftUI

class LoginCoordinator: ObservableObject, Navigable {
    typealias Destination = LoginDestination
    
    @Published var path: NavigationPath = NavigationPath()
    
    func navigate(to destination: LoginDestination) {
        self.path.append(destination)
    }
    
    func navigateBack() {
        guard !self.path.isEmpty else { return }
        
        self.path.removeLast()
    }
}
