//
//  NetworkManager.swift
//  e-commerce
//
//  Created by Kacper Grabiec on 10/10/2025.
//

import Foundation

class NetworkManager {
    private let keychainStorageManager: StorageManager = KeychainStorageManager.shared
    
    private let baseURL: URL = URL(string: "http://192.168.0.47:8080/api")!
    private let urlSession: URLSession = .shared
    
    static let shared = NetworkManager()
    
    private init() {}
    
    private func createRequest(
        to endpoint: Endpoint,
        of method: HTTPMethod,
        attach queryItems: [URLQueryItem]? = nil,
        with body: Data? = nil
    ) throws -> URLRequest {
        var url = baseURL.appendingPathComponent(endpoint.path)
        
        if let queryItems = queryItems, var components = URLComponents(url: url, resolvingAgainstBaseURL: false) {
            components.queryItems = queryItems
            
            if let urlWithQuery = components.url {
                url = urlWithQuery
            }
        }
        
        var request = URLRequest(url: url)
        request.httpMethod = method.rawValue
        request.setValue("application/json", forHTTPHeaderField: "Content-Type")
        
        if endpoint.requiresAuth, let token: String = try? keychainStorageManager.search(
            for: KeychainKey.accessToken,
            as: String.self
        ) {
            request.setValue("Bearer \(token)", forHTTPHeaderField: "Authorization")
        }
        
        request.httpBody = body
        
        return request
    }
    
    func get<T: Decodable>(
        from endpoint: Endpoint,
        attach queryItems: [URLQueryItem]? = nil
    ) async throws -> T {
        let request = try createRequest(
            to: endpoint,
            of: .get,
            attach: queryItems
        )
        let (responseData, response) = try await urlSession.data(for: request)
        
        guard let httpResponse = response as? HTTPURLResponse else {
            throw URLError(.badServerResponse)
        }
        
        guard (200...299).contains(httpResponse.statusCode) else {
            throw URLError(.init(rawValue: httpResponse.statusCode))
        }
        
        return try JSONDecoder().decode(T.self, from: responseData)
    }
    
    func post<T: Encodable>(
        to endpoint: Endpoint,
        with body: T
    ) async throws {
        let data = try JSONEncoder().encode(body)
        let request = try createRequest(
            to: endpoint,
            of: .post,
            with: data
        )
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
        let request = try createRequest(
            to: endpoint,
            of: .post,
            with: data
        )
        let (responseData, response) = try await urlSession.data(for: request)
        
        guard let httpResponse = response as? HTTPURLResponse else {
            throw URLError(.badServerResponse)
        }
        
        guard (200...299).contains(httpResponse.statusCode) else {
            throw URLError(.init(rawValue: httpResponse.statusCode))
        }
        
        return try JSONDecoder().decode(T.self, from: responseData)
    }
    
    func post(to endpoint: Endpoint) async throws {
        let request = try createRequest(
            to: endpoint,
            of: .post
        )
        let (_, response) = try await urlSession.data(for: request)
        
        guard let httpResponse = response as? HTTPURLResponse else {
            throw URLError(.badServerResponse)
        }
        
        guard (200...299).contains(httpResponse.statusCode) else {
            throw URLError(.init(rawValue: httpResponse.statusCode))
        }
    }
    
    func post<T: Decodable>(to endpoint: Endpoint) async throws -> T {
        let request = try createRequest(
            to: endpoint,
            of: .post
        )
        let (responseData, response) = try await urlSession.data(for: request)
        
        guard let httpResponse = response as? HTTPURLResponse else {
            throw URLError(.badServerResponse)
        }
        
        guard (200...299).contains(httpResponse.statusCode) else {
            throw URLError(.init(rawValue: httpResponse.statusCode))
        }
        
        return try JSONDecoder().decode(T.self, from: responseData)
    }
    
    func patch<T: Encodable>(
        to endpoint: Endpoint,
        with body: T
    ) async throws {
        let data = try JSONEncoder().encode(body)
        let request = try createRequest(
            to: endpoint,
            of: .patch,
            with: data
        )
        let (_, response) = try await urlSession.data(for: request)
        
        guard let httpResponse = response as? HTTPURLResponse else {
            throw URLError(.badServerResponse)
        }
        
        guard (200...299).contains(httpResponse.statusCode) else {
            throw URLError(.init(rawValue: httpResponse.statusCode))
        }
    }
    
    func delete(at endpoint: Endpoint) async throws {
        let request = try createRequest(
            to: endpoint,
            of: .delete
        )
        let (_, response) = try await urlSession.data(for: request)
        
        guard let httpResponse = response as? HTTPURLResponse else {
            throw URLError(.badServerResponse)
        }
        
        guard (200...299).contains(httpResponse.statusCode) else {
            throw URLError(.init(rawValue: httpResponse.statusCode))
        }
    }
}
