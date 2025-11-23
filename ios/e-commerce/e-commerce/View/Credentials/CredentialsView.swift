//
//  CredentialsView.swift
//  e-commerce
//
//  Created by Kacper Grabiec on 21/11/2025.
//

import SwiftUI

struct CredentialsView: View {
    @EnvironmentObject private var homeCoordinator: HomeCoordinator
    
    private let email: String
    
    init(email: String) {
        self.email = email
    }
    
    var body: some View {
        List {
            LabeledContent("Email", value: email)
            
            NavigationLink(destination: ChangePasswordView().environmentObject(homeCoordinator)) {
                LabeledContent("Password", value: "********")
            }
        }
        .navigationTitle("Credentials")
    }
}

#Preview {
    NavigationStack {
        CredentialsView(email: "example@email.com")
            .environmentObject(HomeCoordinator())
    }
}
