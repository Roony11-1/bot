package com.bot.commands.gamecommands;

import java.awt.Color;
import java.util.List;

import com.bot.commands.ICommand;
import com.bot.factory.UnidadEmbedFactory;
import com.bot.game.model.Unidad;
import com.bot.game.service.UnidadService;

import lombok.RequiredArgsConstructor;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

@RequiredArgsConstructor
public class UnitListCommand implements ICommand
{
    private final UnidadService _unidadService;
    private final UnidadEmbedFactory _unidadEmbedFactory;

    @Override
    public String name() 
    {
        return "!unidades";
    }

    @Override
    public void help(MessageReceivedEvent event) 
    {
        StringBuilder message = new StringBuilder();
        message.append("Formato: !unidades\n")
               .append("Lista todas las unidades creadas por el usuario.");
        event.getChannel().sendMessage(message.toString()).queue();
    }

    @Override
    public void execute(String[] args, MessageReceivedEvent event) 
    {
        if (args.length > 1 && args[1].equalsIgnoreCase("--help"))
        {
            help(event);
            return;
        }

        String discordId = event.getAuthor().getId();

        listarUnidades(discordId, event);
    }

    private void listarUnidades(String discordId, MessageReceivedEvent event)
    {
        List<Unidad> unidades = _unidadService.findByDiscordId(discordId);

        if (unidades.isEmpty()) 
        {
            event.getChannel().sendMessage("No tienes unidades creadas.").queue();
            return;
        }

        String owner = event.getAuthor().getGlobalName();

        event.getChannel().sendMessageEmbeds(
            _unidadEmbedFactory.mostrarUnidades(owner, unidades, Color.CYAN)
        ).queue();
    }
}
