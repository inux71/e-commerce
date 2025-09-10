package com.grabieckacper.ecommerce.dashboard.response;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public record ProductResponse(
        String name,
        String description,
        BigDecimal price,
        Date createdAt,
        Date updatedAt,
        Long createdBy, // Employee's id
        Long updatedBy, // Employee's id
        List<String> categories,
        List<Long> files
) {}
