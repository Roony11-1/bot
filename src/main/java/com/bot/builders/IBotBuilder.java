package com.bot.builders;

import java.util.ArrayList;

import com.bot.Bot;

import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.requests.GatewayIntent;

public interface IBotBuilder extends IBuilder<Bot>
{
    IBotBuilder setToken(String token);
    IBotBuilder addListener(ListenerAdapter listener);
    IBotBuilder addListeners(ArrayList<ListenerAdapter> listener);
    IBotBuilder enableIntent(GatewayIntent intents);
    IBotBuilder enableIntents(GatewayIntent... intents);
}
