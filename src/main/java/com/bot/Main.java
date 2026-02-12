package com.bot;

import com.bot.admin.service.AdminService;
import com.bot.builders.BotBuilder;
import com.bot.commands.admincommands.SyncUserCommand;
import com.bot.commands.gamecommands.CreateUnitCommand;
import com.bot.commands.gamecommands.DeleteUnitByDiscordIdCommand;
import com.bot.commands.gamecommands.UnitListCommand;
import com.bot.dispatcher.CommandDispatcher;
import com.bot.game.service.UnidadService;
import com.bot.hooks.UpdateMessageStatsHook;
import com.bot.listeners.MemberJoinedtoGuildListener;
import com.bot.listeners.MessageReceiveListener;
import com.bot.service.UnidadEmbedFactory;
import com.bot.service.MemberRoleService;
import com.bot.service.MemberSyncService;

import io.github.cdimascio.dotenv.Dotenv;
import net.dv8tion.jda.api.requests.GatewayIntent;

public class Main 
{
    public static void main(String[] args) 
    {
        Dotenv dotenv = Dotenv.load();
        String token = dotenv.get("DISCORD_TOKEN");
        String ownerId = dotenv.get("DISCORD_OWNER_ID");

        System.setProperty("jdk.tls.client.protocols", "TLSv1.2");

        UnidadService unidadService = new UnidadService();
        AdminService adminService = new AdminService();

        MemberRoleService memberRoleService = new MemberRoleService();
        MemberSyncService memberSyncService = new MemberSyncService(adminService);

        UnidadEmbedFactory embedService = new UnidadEmbedFactory();

        CommandDispatcher dispatcher = new CommandDispatcher()
                .register(new CreateUnitCommand(unidadService, embedService))
                .register(new UnitListCommand(unidadService, embedService))
                .register(new DeleteUnitByDiscordIdCommand(unidadService))
                .register(new SyncUserCommand(ownerId, adminService));

        dispatcher.registerMessageHook(new UpdateMessageStatsHook(adminService));

        Bot bot = new BotBuilder()
                .setToken(token)
                .addListener(
                    new MessageReceiveListener(
                        dispatcher
                    )
                )
                .addListener(
                    new MemberJoinedtoGuildListener(
                        memberSyncService,
                        memberRoleService
                    )
                )
                .enableIntents(
                    GatewayIntent.GUILD_MESSAGES,
                    GatewayIntent.MESSAGE_CONTENT,
                    GatewayIntent.GUILD_MEMBERS
                )
                .build();

        bot.start();
    }
}