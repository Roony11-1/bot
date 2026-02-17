package com.bot.builders;

import net.dv8tion.jda.api.events.message.GenericMessageEvent;

public class InteractionMessageBuilder 
{
    private GenericMessageEvent event;

    public void send()
    {
        event
            .getChannel()
            .sendMessage("Creado con el builder")
            .queue();
    }
}