package com.grabieckacper.ecommerce.dashboard.response;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public record ProductResponse(
        Long id,
        String name,
        String description,
        BigDecimal price,
        Date createdAt,
        Date updatedAt,
        String createdBy, // Employee's email
        String updatedBy, // Employee's email
        List<String> categories
) {}
