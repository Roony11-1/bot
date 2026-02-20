package com.bot.comidas.model;

import lombok.Data;

@Data
public class Macronutrients 
{
    private double calories; // kcal
    private double protein; // g
    private double fat; // g
    private double carbs; // g

    private double sodium; // mg
    private double sugar; // gr
}
