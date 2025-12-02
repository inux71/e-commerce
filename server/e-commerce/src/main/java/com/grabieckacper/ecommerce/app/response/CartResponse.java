package com.grabieckacper.ecommerce.app.response;

import java.util.List;

public record CartResponse(List<CartProductResponse> cartProducts) {}
