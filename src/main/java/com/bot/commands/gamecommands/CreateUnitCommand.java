package com.bot.commands.gamecommands;

import java.util.Arrays;

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
    public int minArgs() 
    {
        return 2;
    }

    @Override
    public void execute(String[] args, MessageReceivedEvent event) 
    {
        String name = String.join(" ", Arrays.copyOfRange(args, 1, args.length));

        var menu = _stringSelectMenuFactory.createUnitMenu(name);

        event.getChannel()
            .sendMessage("Elige la clase de tu unidad:")
            .setActionRow(menu)
            .queue();
    }

    @Override
    public void help(MessageReceivedEvent event) 
    {
        event.getChannel().sendMessage("Formato: !create <name>").queue();
    }
}
