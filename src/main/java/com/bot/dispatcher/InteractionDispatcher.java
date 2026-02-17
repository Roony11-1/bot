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
        String componentId = event.getComponentId();

        for (Map.Entry<String, IInteractionHandler> entry : interactions.entrySet())
        {
            if (componentId.startsWith(entry.getKey()))
            {
                entry.getValue().handle(event);
                return;
            }
        }
    }
}
