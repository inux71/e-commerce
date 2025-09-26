//
//  RegisterViewModel.swift
//  e-commerce
//
//  Created by Kacper Grabiec on 26/09/2025.
//

import Combine
import Foundation

class RegisterViewModel: ObservableObject {
    @Published var firstName: String = ""
    @Published var lastName: String = ""
    @Published var email: String = ""
    @Published var password: String = ""
    
    func signUp() {
        
    }
}
