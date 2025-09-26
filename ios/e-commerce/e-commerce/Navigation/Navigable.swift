//
//  Navigable.swift
//  e-commerce
//
//  Created by Kacper Grabiec on 26/09/2025.
//

import Foundation
import SwiftUI

protocol Navigable {
    associatedtype Destination: Hashable
    
    var path: NavigationPath { get }
    
    func navigate(to destination: Destination)
    func navigateBack()
}
