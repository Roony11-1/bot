package com.bot.game.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Class 
{
    private String name;
    private Estadisticas estadisticasBase;

    @Override
    public String toString()
    {
        StringBuilder info = new StringBuilder();

        StringBuilder statsInfo = new StringBuilder();

        statsInfo.append("--- Puntos de golpe: ").append(this.estadisticasBase.getPv()).append("\n");

        info.append("-- Nombre de la clase: ").append(name).append("\n")
            .append("-- Estadísticas base: ").append("\n")
            .append(statsInfo.toString());

        return info.toString();
    }
}
