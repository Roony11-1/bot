package com.bot.commands.gamecommands;

import java.util.Optional;

import com.bot.commands.ICommand;
import com.bot.game.model.Unidad;
import com.bot.game.service.UnidadService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

@RequiredArgsConstructor
@Slf4j
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
        Optional<Integer> nivel = paseInteger(args[2], event);

        if (nivel.isEmpty())
            return;

        crearUnidad(
            nombre, discordId, nivel.get(), event);
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
            log.error("Error al crear unidad", e);
            event.getChannel().sendMessage("Ocurrió un error: " + e.toString()).queue();
        }
    }

    @Override
    public void help(MessageReceivedEvent event) 
    {
        event.getChannel().sendMessage("Formato: !create <nombre> <nivel>").queue();
    }

    private Optional<Integer> paseInteger(String number, MessageReceivedEvent event)
    {
        try
        {
            return Optional.of(Integer.parseInt(number));
        }
        catch (NumberFormatException e)
        {
            event.getChannel()
                .sendMessage("El valor que ingresaste no es un número.")
                .queue();
                
            help(event);

            log.error("Error al obtener el valor númerico");

            return Optional.empty();
        }
    }
}
