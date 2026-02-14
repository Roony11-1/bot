package com.bot.factory;

import java.awt.Color;
import java.util.List;

import com.bot.game.model.Unidad;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;


public class UnidadEmbedFactory 
{
    private EmbedBuilder unitInfo(EmbedBuilder message, Unidad unidad, String title)
    {
        return message.addField(
            title,
            unidad.getInfoGeneral(),
            false
        );
    } 

    public MessageEmbed mostrarUnidad(Unidad unidad, Color color)
    {
        EmbedBuilder builder = new EmbedBuilder()
                .setTitle("Personaje creado: " + unidad.getNombre())
                .setColor(color);

        return unitInfo(builder, unidad, "Datos Generales").build();
    }

    public MessageEmbed mostrarUnidades(String owner, List<Unidad> unidades, Color color)
    {
        EmbedBuilder builder = new EmbedBuilder()
                .setTitle("Unidades de "+owner)
                .setColor(color);

        unidades.forEach(unidad -> unitInfo(builder, unidad, unidad.getNombre()));

        return builder.build();
    }
}
