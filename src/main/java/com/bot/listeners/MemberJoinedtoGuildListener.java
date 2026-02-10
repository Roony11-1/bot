package com.bot.listeners;

import com.bot.service.MemberRoleService;
import com.bot.service.MemberSyncService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

@RequiredArgsConstructor
@Slf4j
public class MemberJoinedtoGuildListener extends ListenerAdapter
{
    private final MemberSyncService _memberSyncService;
    private final MemberRoleService _memberRoleService;

    @Override
    public void onGuildMemberJoin(GuildMemberJoinEvent event) 
    {
        try 
        {
            _memberRoleService.assignDefaultRole(event);
            _memberSyncService.syncMemberData(event);
        } 
        catch (Exception e) 
        {
            System.out.println("Error al procesar la entrada de miembro: " + e.getMessage());
        }

    }
}
