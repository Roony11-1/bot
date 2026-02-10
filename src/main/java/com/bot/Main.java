package com.bot;

import java.util.List;

import com.bot.admin.service.AdminService;
import com.bot.commands.admincommands.SyncUserCommand;
import com.bot.commands.gamecommands.CreateUnitCommand;
import com.bot.commands.gamecommands.DeleteUnitByDiscordIdCommand;
import com.bot.commands.gamecommands.UnitListCommand;
import com.bot.dispatcher.CommandDispatcher;
import com.bot.game.service.UnidadService;
import com.bot.hooks.UpdateMessageStatsHook;
import com.bot.listeners.MemberJoinedtoGuildListener;
import com.bot.listeners.MessageReceiveListener;
import com.bot.service.MemberRoleService;
import com.bot.service.MemberSyncService;

import io.github.cdimascio.dotenv.Dotenv;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class Main 
{
    public static void main(String[] args) 
    {
        Dotenv dotenv = Dotenv.load();
        String token = dotenv.get("DISCORD_TOKEN");
        String ownerId = dotenv.get("DISCORD_OWNER_ID");

        UnidadService unidadService = new UnidadService();
        AdminService adminService = new AdminService();

        MemberRoleService memberRoleService = new MemberRoleService();
        MemberSyncService memberSyncService = new MemberSyncService(adminService);

        CommandDispatcher dispatcher = new CommandDispatcher()
                .register(new CreateUnitCommand(unidadService))
                .register(new UnitListCommand(unidadService))
                .register(new DeleteUnitByDiscordIdCommand(unidadService))
                .register(new SyncUserCommand(ownerId, adminService));

        dispatcher.registerMessageHook(new UpdateMessageStatsHook(adminService));

        List<ListenerAdapter> listeners = List.of(
            MessageReceiveListener.builder()
                ._commandDispatcher(dispatcher)
                .build(),
            new MemberJoinedtoGuildListener(
                memberSyncService,
                memberRoleService));

        Bot bot = new Bot(token, listeners);

        bot.start();
     }
}