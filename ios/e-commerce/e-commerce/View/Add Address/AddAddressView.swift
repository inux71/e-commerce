//
//  AddAddressView.swift
//  e-commerce
//
//  Created by Kacper Grabiec on 02/01/2026.
//

import SwiftUI

struct AddAddressView: View {
    @EnvironmentObject private var homeCoordinator: HomeCoordinator
    
    @StateObject private var viewModel: AddAddressViewModel = AddAddressViewModel()
    
    var body: some View {
        NavigationStack {
            List {
                NavigationLink(destination: SelectCountryView(
                    countries: viewModel.countries,
                    selectedCountry: $viewModel.selectedCountry
                )) {
                    LabeledContent("Country", value: viewModel.selectedCountryText)
                }
                
                LabeledContent("Postal Code") {
                    TextField(
                        "Postal Code",
                        text: $viewModel.postalCode,
                        prompt: Text("00-000")
                    )
                    .keyboardType(.numbersAndPunctuation)
                    .multilineTextAlignment(.trailing)
                    .textContentType(.postalCode)
                }
                
                NavigationLink(destination: SelectCityView(
                    cities: viewModel.cities,
                    selectedCity: $viewModel.selectedCity
                )) {
                    LabeledContent("City", value: viewModel.selectedCityText)
                }
                .disabled(viewModel.pickingCityDisabled)
                
                LabeledContent("Street") {
                    TextField(
                        "Street",
                        text: $viewModel.street,
                        prompt: Text("ul. Warszawska 35")
                    )
                    .multilineTextAlignment(.trailing)
                    .textContentType(.fullStreetAddress)
                }
                
                Section {
                    Button("Save", systemImage: "sdcard") {
                        Task {
                            await viewModel.saveAddress()
                        }
                    }
                }
            }
            .alert(
                viewModel.errorMessage ?? "",
                isPresented: $viewModel.isAlertPresented
            ) {
                Button("OK") {
                    viewModel.isAlertPresented = false
                    viewModel.errorMessage = nil
                }
            }
            .alert(
                "Address saved successfully!",
                isPresented: $viewModel.addressSaved
            ) {
                Button("OK") {
                    homeCoordinator.dismissSheetableItem()
                }
            }
            .navigationTitle("Add address")
            .onChange(of: viewModel.selectedCountry) {
                Task {
                    await viewModel.getCities()
                }
            }
            .overlay {
                if viewModel.isLoading {
                    ProgressView()
                }
            }
            .task {
                await viewModel.getCountries()
            }
        }
    }
}

#Preview {
    AddAddressView()
        .environmentObject(HomeCoordinator())
}
