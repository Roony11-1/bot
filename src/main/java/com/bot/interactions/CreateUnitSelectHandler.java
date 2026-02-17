package com.bot.interactions;

import java.awt.Color;
import java.util.concurrent.ThreadLocalRandom;

import com.bot.factory.UnidadEmbedFactory;
import com.bot.game.model.Unidad;
import com.bot.game.service.UnidadService;

import lombok.RequiredArgsConstructor;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.interaction.component.StringSelectInteractionEvent;

@RequiredArgsConstructor
public class CreateUnitSelectHandler implements IInteractionHandler
{
    private final UnidadEmbedFactory _unidadEmbedFactory;
    private final UnidadService _unidadService;

    @Override
    public String componentId() 
    {
        return "create-unit-menu";
    }

    @Override
    public void handle(StringSelectInteractionEvent event) 
    {
        String[] parts = event.getComponentId().split(":");

        String name = parts[1];
        String selectedClass = event.getValues().get(0);

        event.deferReply(true).queue();

        Unidad unidad = _unidadService
                .createUnit(
                    event.getUser().getId(), 
                    selectedClass, 
                    name)
                .orElseThrow();

        MessageEmbed embed = _unidadEmbedFactory
                .mostrarUnidad(unidad, Color.GREEN);

        event.getHook()
            .sendMessageEmbeds(embed)
            .queue();
    }
}
