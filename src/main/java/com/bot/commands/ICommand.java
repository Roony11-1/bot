package com.bot.commands;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public interface ICommand 
{
    String name();
    void execute(String[] args, MessageReceivedEvent event);
    void help(MessageReceivedEvent event);
}