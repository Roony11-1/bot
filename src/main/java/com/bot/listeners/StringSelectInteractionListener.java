package com.bot.listeners;

import com.bot.dispatcher.InteractionDispatcher;

import lombok.RequiredArgsConstructor;
import net.dv8tion.jda.api.events.interaction.component.StringSelectInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

@RequiredArgsConstructor
public class StringSelectInteractionListener extends ListenerAdapter
{
    private final InteractionDispatcher _interactionDispatcher;

    @Override
    public void onStringSelectInteraction(StringSelectInteractionEvent event) 
    {
        _interactionDispatcher.execute(event);
    }
}
