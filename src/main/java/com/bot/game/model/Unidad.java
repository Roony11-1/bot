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
    private Class clase;

    @Override
    public String toString()
    {
        StringBuilder text = new StringBuilder();

        text.append("-- Nombre: ").append(nombre).append("\n")
            .append("-- Nivel: ").append(nivel).append("\n")
            .append(this.clase.toString());

        return text.toString();
    }
}
