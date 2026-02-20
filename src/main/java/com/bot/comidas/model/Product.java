package com.bot.comidas.model;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class Product 
{
    private String id;

    private String name;
    private String brand;
    private List<String> categoryIds = new ArrayList<>();

    private MeasurementUnit baseUnit; // GRAMS o MILLILITERS

    private Macronutrients macrosPer100Units;

    private Instant createdAt;
}
