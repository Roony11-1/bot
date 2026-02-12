package com.bot.listeners;

import com.bot.dispatcher.CommandDispatcher;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

@Slf4j
@RequiredArgsConstructor
public class MessageReceiveListener extends ListenerAdapter 
{
    private final CommandDispatcher _commandDispatcher;

    @Override
    public void onMessageReceived(MessageReceivedEvent event) 
    {
        if (event.getAuthor().isBot()) 
            return;

        _commandDispatcher.execute(event);
    }
}
