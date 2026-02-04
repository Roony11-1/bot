package com.bot.dispatcher;

import java.util.HashMap;
import java.util.Map;

import com.bot.commands.ICommand;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class CommandDispatcher 
{
    private final Map<String, ICommand> _commands = new HashMap<>();

    public CommandDispatcher register(ICommand command)
    {
        _commands.put(command.name(), command);
        return this;
    }

    public void execute(MessageReceivedEvent event)
    {
        String content = event.getMessage().getContentRaw();

        String[] parts = content.split(" ");

        ICommand command = _commands.get(parts[0]);

        if (command != null)
            command.execute(parts, event);
    }
}
