package com.bot.commands;

import java.util.Optional;

import com.bot.game.model.Unidad;
import com.bot.game.service.UnidadService;

import lombok.RequiredArgsConstructor;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

@RequiredArgsConstructor
public class CreateUnitCommand implements ICommand
{
    private final UnidadService _unidadService;

    @Override
    public String name() 
    {
        return "!create";
    }

    @Override
    public void execute(String[] args, MessageReceivedEvent event) 
    {
        if (args.length < 3 || args[1].equalsIgnoreCase("--help"))
        {
            help(event);
            return;
        }

        String discordId = event.getAuthor().getId();

        String nombre = args[1];
        Integer nivel = paseInteger(args[2], event);

        if (nivel == null)
            return;

        crearUnidad(
            nombre, discordId, nivel, event);
    }

    private void crearUnidad(String nombre, String discordId, Integer nivel, MessageReceivedEvent event) 
    {
        try 
        {
            Optional<Unidad> unidad = _unidadService.createUnit(discordId, nombre, nivel);

            StringBuilder message = new StringBuilder();

            if (unidad.isPresent()) 
            {
                message.append("Unidad Creada Exitosamente")
                .append("\n")
                .append(unidad.get().toString());
                event.getChannel().sendMessage(message.toString()).queue();
            }
        } 
        catch (Exception e) 
        {
            e.printStackTrace();
            event.getChannel().sendMessage("Ocurrió un error: " + e.toString()).queue();
        }
    }

    @Override
    public void help(MessageReceivedEvent event) 
    {
        event.getChannel().sendMessage("Formato: !create <nombre> <nivel>").queue();
    }

    private Integer paseInteger(String number, MessageReceivedEvent event)
    {
        try
        {
            return Integer.parseInt(number);
        }
        catch (NumberFormatException e)
        {
            event.getChannel()
                .sendMessage("El valor que ingresaste no es un número.")
                .queue();
            help(event);
            return null;
        }
    }
}
