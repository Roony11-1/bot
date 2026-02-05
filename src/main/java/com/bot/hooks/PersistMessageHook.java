package com.bot.hooks;

import com.bot.admin.service.AdminService;

import com.bot.dispatcher.CommandDispatcher;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Instant;

@RequiredArgsConstructor
@Slf4j
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
            log.error(
                "Error actualizando estadísticas de mensajes. discordId={}, serverId={}",
                discordId,
                serverId,
                e);
        }
    }
}
