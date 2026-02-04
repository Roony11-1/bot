package com.bot.listeners;

import com.bot.admin.UserDTO;
import com.bot.admin.service.AdminService;

import lombok.RequiredArgsConstructor;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

@RequiredArgsConstructor
public class MemberJoinedtoGuildListener extends ListenerAdapter
{
    private final AdminService _adminService;

@Override
public void onGuildMemberJoin(GuildMemberJoinEvent event) {
    Member member = event.getMember();
    User user = event.getUser();

    String discordId = user.getId();
    String serverId = event.getGuild().getId();

    // Buscar usuario existente
    UserDTO existingUser = _adminService.findByDiscordIdAndServerId(discordId, serverId)
            .orElse(null);

    UserDTO userDTO;
    if (existingUser != null) 
    {
        // Actualizar info básica pero mantener messageCount y lastMessageAt
        existingUser.setUsername(user.getName());
        existingUser.setGlobalName(user.getGlobalName());
        existingUser.setNickname(member.getNickname());
        existingUser.setBot(user.isBot());
        existingUser.setOwner(event.getGuild().getOwnerId().equals(user.getId()));
        existingUser.setAdmin(member.hasPermission(Permission.ADMINISTRATOR));
        existingUser.setJoinedAt(member.getTimeJoined().toInstant());
        userDTO = existingUser;
    } 
    else 
    {
        userDTO = UserDTO.builder()
                .discordId(discordId)
                .serverId(serverId)
                .username(user.getName())
                .globalName(user.getGlobalName())
                .nickname(member.getNickname())
                .isBot(user.isBot())
                .isOwner(event.getGuild().getOwnerId().equals(user.getId()))
                .isAdmin(member.hasPermission(Permission.ADMINISTRATOR))
                .joinedAt(member.getTimeJoined().toInstant())
                .lastSync(null)
                .lastMenssageAt(null)
                .messageCount(0)
                .build();
    }

    UserDTO savedUser = _adminService.save(userDTO)
            .orElseThrow(() -> new RuntimeException("Error saving user"));

    System.out.println("Nuevo miembro unido al servidor:\n" + savedUser);
}
}
