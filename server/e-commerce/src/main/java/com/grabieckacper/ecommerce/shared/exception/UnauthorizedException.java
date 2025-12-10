package com.grabieckacper.ecommerce.shared.exception;

import jakarta.persistence.PersistenceException;

public class UnauthorizedException extends PersistenceException {
    public UnauthorizedException() {}
}
