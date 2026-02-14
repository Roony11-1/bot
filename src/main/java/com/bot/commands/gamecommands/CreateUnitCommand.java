package com.bot.commands.gamecommands;

import com.bot.commands.ICommand;
import com.bot.factory.StringSelectMenuFactory;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

@RequiredArgsConstructor
@Slf4j
public class CreateUnitCommand implements ICommand
{
    private final StringSelectMenuFactory _stringSelectMenuFactory;

    @Override
    public String name() 
    {
        return "!create";
    }

    @Override
    public void execute(String[] args, MessageReceivedEvent event) 
    {
        if (args.length > 1 && args[1].equalsIgnoreCase("--help"))
        {
            help(event);
            return;
        }

        var menu = _stringSelectMenuFactory.createUnitMenu();

        event.getChannel()
            .sendMessage("Elige la clase de tu unidad:")
            .setActionRow(menu)
            .queue();
    }

    @Override
    public void help(MessageReceivedEvent event) 
    {
        event.getChannel().sendMessage("Formato: !create").queue();
    }
}
