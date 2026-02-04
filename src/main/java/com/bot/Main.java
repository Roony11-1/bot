package com.bot;

import java.util.List;

import com.bot.commands.CreateUnitCommand;
import com.bot.commands.DeleteUnitByDiscordIdCommand;
import com.bot.commands.UnitListCommand;
import com.bot.dispatcher.CommandDispatcher;
import com.bot.game.service.UnidadService;
import com.bot.listeners.MessageReceiveListener;

import io.github.cdimascio.dotenv.Dotenv;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class Main 
{
    public static void main(String[] args) 
    {
        Dotenv dotenv = Dotenv.load();
        String token = dotenv.get("DISCORD_TOKEN");

        UnidadService unidadService = new UnidadService();

        List<ListenerAdapter> listeners = List.of(
            MessageReceiveListener.builder()
                ._commandDispatcher(new CommandDispatcher()
                    .register(new CreateUnitCommand(unidadService))
                    .register(new UnitListCommand(unidadService))
                    .register(new DeleteUnitByDiscordIdCommand(unidadService)))
                .build()
        );

        Bot bot = new Bot(token, listeners);

        bot.start();
    }
}