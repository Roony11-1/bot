package com.bot.commands.gamecommands;

import java.util.Optional;

import com.bot.commands.ICommand;
import com.bot.game.service.UnidadService;

import lombok.RequiredArgsConstructor;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

@RequiredArgsConstructor
public class DeleteUnitByDiscordIdCommand implements ICommand
{
    private final UnidadService _unidadService;
    
    @Override
    public String name() 
    {
        return "!borrar";
    }

    @Override
    public void help(MessageReceivedEvent event) 
    {
        StringBuilder message = new StringBuilder();
        message.append("Formato: !borrar\n")
               .append("Borra todas las unidades creadas por el usuario.");
        event.getChannel().sendMessage(message.toString()).queue();
    }

    @Override
    public int minArgs() 
    {
        return 1;
    }

    @Override
    public void execute(String[] args, MessageReceivedEvent event)
    {
        borrarUnidades(event.getAuthor().getId(), event);
    }

    private void borrarUnidades(String discordId, MessageReceivedEvent event)
    {
        Optional<Long> total = _unidadService.deleteByDiscordId(discordId);

        if (total.isPresent())
            event.getChannel().sendMessage("Se han borrado " + total.get() + " unidades.").queue();
        else
            event.getChannel().sendMessage("No se han borrado unidades.").queue();
    }
}
