package com.bot.hooks;

import com.bot.admin.service.AdminService;

import com.bot.dispatcher.CommandDispatcher;

import lombok.RequiredArgsConstructor;

import java.time.Instant;

@RequiredArgsConstructor
public class PersistMessageHook implements CommandDispatcher.IMessageHook
{
    private final AdminService _adminService;

    @Override
    public void onMessageReceived(String discordId, String serverId) 
    {
        try 
        {
            _adminService.updateMessageStats(discordId, serverId, Instant.now());
        } 
        catch (Exception e) 
        {
            System.err.println("Error persisting message for " + discordId + ": " + e.getMessage());
            e.printStackTrace();
        }
    }
}
