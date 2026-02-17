package com.bot.commands;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public interface ICommand 
{
    String name();
    void execute(String[] args, MessageReceivedEvent event);
    void help(MessageReceivedEvent event);

    default int minArgs()
    {
        return 0;
    }

    default boolean validate(String[] args, MessageReceivedEvent event)
    {
        // Help global automático
        if (args.length >= 2 && isHelp(args[1]))
        {
            help(event);
            return false;
        }

        if (args.length < minArgs())
        {
            help(event);
            return false;
        }

        return true;
    }

    private boolean isHelp(String arg)
    {
        arg = arg.toLowerCase();

        return arg.equals("--help") ||
            arg.equals("-h") ||
            arg.equals("help");
    }
}