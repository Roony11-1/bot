package com.bot.interactions;

import net.dv8tion.jda.api.events.interaction.component.StringSelectInteractionEvent;

public interface IInteractionHandler 
{
    String componentId();
    void handle(StringSelectInteractionEvent event);
}
