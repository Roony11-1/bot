package com.bot.listeners;

import com.bot.admin.UserDTO;
import com.bot.admin.service.AdminService;

import lombok.RequiredArgsConstructor;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

@RequiredArgsConstructor
public class MemberJoinedtoGuildListener extends ListenerAdapter
{
    private final AdminService _adminService;

    @Override
    public void onGuildMemberJoin(GuildMemberJoinEvent event) 
    {
        String discordId = event.getUser().getId();
        String serverId = event.getGuild().getId();

        // Buscar usuario existente
        UserDTO userDTO = _adminService
                .findByDiscordIdAndServerId(discordId, serverId)
                .map(existing -> updateExistingUser(existing, event)) // Si existes acutializamos
                .orElseGet(() -> createUser(event)); // Si no creamos

        UserDTO savedUser = _adminService.save(userDTO)
                .orElseThrow(() -> new RuntimeException("Error al guardar el usuario de discordId: " + discordId + " en el servidor: " + serverId));

        System.out.println("Nuevo miembro unido al servidor:\n" + savedUser.toString());
    }

    private boolean isOwner(GuildMemberJoinEvent event)
    {
        return event.getGuild().getOwnerId() != null
            && event.getGuild().getOwnerId().equals(event.getUser().getId());
    }

    private UserDTO updateExistingUser(UserDTO existingUser, GuildMemberJoinEvent event)
    {
        existingUser.setUsername(event.getUser().getName());
        existingUser.setGlobalName(event.getUser().getGlobalName());
        existingUser.setNickname(event.getMember().getNickname());
        existingUser.setBot(event.getUser().isBot());
        existingUser.setOwner(isOwner(event));
        existingUser.setAdmin(event.getMember().hasPermission(Permission.ADMINISTRATOR));
        existingUser.setJoinedAt(event.getMember().getTimeJoined().toInstant());

        return existingUser;
    }

    private UserDTO createUser(GuildMemberJoinEvent event)
    {
        return UserDTO.builder()
                .discordId(event.getUser().getId())
                .serverId(event.getGuild().getId())
                .username(event.getUser().getName())
                .globalName(event.getUser().getGlobalName())
                .nickname(event.getMember().getNickname())
                .isBot(event.getUser().isBot())
                .isOwner(isOwner(event))
                .isAdmin(event.getMember().hasPermission(Permission.ADMINISTRATOR))
                .joinedAt(event.getMember().getTimeJoined().toInstant())
                .lastSync(null)
                .lastMenssageAt(null)
                .messageCount(0)
                .build();
    }
}
