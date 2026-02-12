package com.bot.builders;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

import com.bot.Bot;

import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.requests.GatewayIntent;

public class BotBuilder implements IBotBuilder
{
    private String discordToken;
    private final List<ListenerAdapter> _listeners = new ArrayList<>();
    private final EnumSet<GatewayIntent> _intents = EnumSet.noneOf(GatewayIntent.class);

    public IBotBuilder setToken(String token)
    {
        this.discordToken = token;

        return this;
    }

    public IBotBuilder addListener(ListenerAdapter listener)
    {
        this._listeners.add(listener);

        return this;
    }

    public IBotBuilder addListeners(ArrayList<ListenerAdapter> listeners)
    {
        this._listeners.addAll(listeners);

        return this;
    }

    public IBotBuilder enableIntent(GatewayIntent intent) 
    {
        this._intents.add(intent);

        return this;
    }

    public IBotBuilder enableIntents(GatewayIntent... intents) 
    {
        this._intents.addAll(List.of(intents));
        
        return this;
    }

    public Bot build() 
    {
        if (discordToken == null)
            throw new IllegalStateException("Token de acceso a discord no definido!");

        return new Bot(discordToken, List.copyOf(_listeners), EnumSet.copyOf(_intents));
    }
}
