package com.bot;

import java.util.EnumSet;
import java.util.List;

import lombok.RequiredArgsConstructor;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.requests.GatewayIntent;

@RequiredArgsConstructor
public class Bot 
{
    private final String _token;
    private final List<ListenerAdapter> _listeners;

    public void start()
    {
        if (_token == null || _token.isBlank())
            throw new IllegalStateException("Falta DISCORD_TOKEN");

        try 
        {
            JDABuilder builder = JDABuilder.createDefault(
                _token,
                EnumSet.of(
                    GatewayIntent.GUILD_MESSAGES,
                    GatewayIntent.MESSAGE_CONTENT));

            _listeners.forEach(listener -> builder.addEventListeners(listener));

            builder.build().awaitReady();
        } 
        catch (Exception e) 
        {
            e.printStackTrace();
        }
    }
}
