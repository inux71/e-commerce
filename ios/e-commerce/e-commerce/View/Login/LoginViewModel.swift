//
//  LoginViewModel.swift
//  e-commerce
//
//  Created by Kacper Grabiec on 26/09/2025.
//

import Combine
import Foundation

class LoginViewModel: ObservableObject {
    @Published var email: String = ""
    @Published var password: String = ""
    
    func signIn() {
        
    }
}
