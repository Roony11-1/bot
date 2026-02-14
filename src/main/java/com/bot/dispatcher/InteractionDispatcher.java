package com.bot.dispatcher;

import java.util.HashMap;
import java.util.Map;

import com.bot.interactions.IInteractionHandler;

import net.dv8tion.jda.api.events.interaction.component.StringSelectInteractionEvent;

public class InteractionDispatcher 
{
    private final Map<String, IInteractionHandler> interactions = new HashMap<>();
    
    public InteractionDispatcher register(IInteractionHandler interaction)
    {
        this.interactions.put(interaction.componentId(), interaction);

        return this;
    }

    public void execute(StringSelectInteractionEvent event)
    {
        IInteractionHandler interaction = interactions.get(event.getComponentId());

        if (interaction != null)
            interaction.handle(event);
    }
}
