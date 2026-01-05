//
//  AddAddressViewModel.swift
//  e-commerce
//
//  Created by Kacper Grabiec on 02/01/2026.
//

import Combine
import Foundation

class AddAddressViewModel: ObservableObject {
    private let networkManager: NetworkManager = NetworkManager.shared
    
    @Published var isLoading: Bool = false
    @Published var isAlertPresented: Bool = false
    @Published var errorMessage: String? = nil
    
    @Published var countries: [Country] = []
    @Published var selectedCountry: Country? = nil
    @Published var cities: [City] = []
    @Published var selectedCity: City? = nil
    
    @Published var postalCode: String = ""
    @Published var street: String = ""
    
    @Published var addressSaved: Bool = false
    
    var selectedCountryText: String {
        guard let selectedCountry else {
            return ""
        }
        
        return "\(selectedCountry.iso2) - \(selectedCountry.name)"
    }
    
    var selectedCityText: String {
        guard let selectedCity else {
            return ""
        }
        
        return selectedCity.name
    }
    
    var pickingCityDisabled: Bool {
        selectedCountry == nil
    }
    
    @MainActor
    func getCountries() async {
        isLoading = true
        
        defer {
            isLoading = false
        }
        
        do {
            let countries: [Country] = try await networkManager.get(from: .countries)
            
            self.countries = countries
        } catch {
            isAlertPresented = true
            errorMessage = error.localizedDescription
        }
    }
    
    @MainActor
    func getCities() async {
        isLoading = true
        
        defer {
            isLoading = false
        }
        
        do {
            guard let selectedCountry else {
                return
            }
            
            let cities: [City] = try await networkManager.get(from: .cities(countryId: selectedCountry.id))
            
            self.cities = cities
        } catch {
            isAlertPresented = true
            errorMessage = error.localizedDescription
        }
    }
    
    @MainActor
    func saveAddress() async {
        isLoading = true
        
        defer {
            isLoading = false
        }
        
        do {
            guard let selectedCity else {
                return
            }
            
            let createAddressRequest: CreateAddressRequest = CreateAddressRequest(
                postalCode: postalCode,
                cityId: selectedCity.id,
                street: street
            )
            
            try await networkManager.post(
                to: .createAddress,
                with: createAddressRequest
            )
            
            addressSaved = true
        } catch {
            isAlertPresented = true
            errorMessage = error.localizedDescription
        }
    }
}
