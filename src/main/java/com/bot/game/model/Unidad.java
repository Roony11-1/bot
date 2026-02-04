package com.bot.game.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Unidad 
{
    private String id;
    private String discordId;
    private String nombre;
    private int nivel;

    @Override
    public String toString()
    {
        StringBuilder text = new StringBuilder();

        return text.append("-- Nombre: ")
            .append(nombre)
            .append("\n-- Nivel: ")
            .append(nivel)
            .append("\n")
            .toString();
    }
}
