//
//  NetworkManager.swift
//  e-commerce
//
//  Created by Kacper Grabiec on 10/10/2025.
//

import Foundation

class NetworkManager {
    private let baseURL: URL = URL(string: "http://192.168.1.111:8080/api")!
    private let urlSession: URLSession = .shared
    
    static let shared = NetworkManager()
    
    private init() {}
    
    private func createRequest(
        to endpoint: Endpoint,
        of method: HTTPMethod,
        with body: Data? = nil
    ) throws -> URLRequest {
        let url = baseURL.appendingPathComponent(endpoint.rawValue)
        
        var request = URLRequest(url: url)
        request.httpMethod = method.rawValue
        request.setValue("application/json", forHTTPHeaderField: "Content-Type")
        request.httpBody = body
        
        return request
    }
    
    func post<T: Encodable>(
        to endpoint: Endpoint,
        with body: T
    ) async throws {
        let data = try JSONEncoder().encode(body)
        let request = try createRequest(to: endpoint, of: .post, with: data)
        let (_, response) = try await urlSession.data(for: request)
        
        guard let httpResponse = response as? HTTPURLResponse else {
            throw URLError(.badServerResponse)
        }
        
        guard (200...299).contains(httpResponse.statusCode) else {
            throw URLError(.init(rawValue: httpResponse.statusCode))
        }
    }
    
    func post<S: Encodable, T: Decodable>(
        to endpoint: Endpoint,
        with body: S
    ) async throws -> T {
        let data = try JSONEncoder().encode(body)
        let request = try createRequest(to: endpoint, of: .post, with: data)
        let (responseData, response) = try await urlSession.data(for: request)
        
        guard let httpResponse = response as? HTTPURLResponse else {
            throw URLError(.badServerResponse)
        }
        
        guard (200...299).contains(httpResponse.statusCode) else {
            throw URLError(.init(rawValue: httpResponse.statusCode))
        }
        
        return try JSONDecoder().decode(T.self, from: responseData)
    }
}
