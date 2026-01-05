//
//  SelectCityView.swift
//  e-commerce
//
//  Created by Kacper Grabiec on 02/01/2026.
//

import SwiftUI

struct SelectCityView: View {
    @Environment(\.dismiss) private var dismiss: DismissAction
    
    var cities: [City]
    @Binding var selectedCity: City?
    
    @State var searchedCity: String = ""
    
    init(cities: [City], selectedCity: Binding<City?>) {
        self.cities = cities
        self._selectedCity = selectedCity
    }
    
    var filteredCities: [City] {
        guard !searchedCity.isEmpty else {
            return cities
        }
        
        return cities.filter { city in
            city.name
                .lowercased()
                .contains(searchedCity.lowercased())
        }
    }
    
    var body: some View {
        List(filteredCities) { city in
            Button(city.name) {
                selectedCity = city
                
                dismiss()
            }
            .buttonStyle(.plain)
        }
        .navigationTitle("Select City")
        .searchable(
            text: $searchedCity,
            prompt: "Search City"
        )
    }
}

#Preview {
    NavigationStack {
        SelectCityView(
            cities: [],
            selectedCity: Binding.constant(nil)
        )
    }
}
