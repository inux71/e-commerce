//
//  SelectCountryView.swift
//  e-commerce
//
//  Created by Kacper Grabiec on 02/01/2026.
//

import SwiftUI

struct SelectCountryView: View {
    @Environment(\.dismiss) private var dismiss: DismissAction
    
    var countries: [Country]
    @Binding var selectedCountry: Country?
    
    @State var searchedCountry: String = ""
    
    init(countries: [Country], selectedCountry: Binding<Country?>) {
        self.countries = countries
        self._selectedCountry = selectedCountry
    }
    
    var filteredCounties: [Country] {
        guard !searchedCountry.isEmpty else {
            return countries
        }
        
        return countries.filter { country in
            country.name
                .lowercased()
                .contains(searchedCountry.lowercased())
            || country.iso2
                .lowercased()
                .contains(searchedCountry.lowercased())
        }
    }
    
    var body: some View {
        List(filteredCounties) { country in
            Button("\(country.iso2) - \(country.name)") {
                selectedCountry = country
                
                dismiss()
            }
            .buttonStyle(.plain)
        }
        .navigationTitle("Select Country")
        .searchable(
            text: $searchedCountry,
            prompt: "Search Country"
        )
    }
}

#Preview {
    NavigationStack {
        SelectCountryView(
            countries: [],
            selectedCountry: Binding.constant(nil)
        )
    }
}
