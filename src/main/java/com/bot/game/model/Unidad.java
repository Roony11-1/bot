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
    private Clase clase;

    public String getInfoGeneral()
    {
        return "Nivel: " + nivel +
            "\nClase: " + clase.getName();
    }
}
