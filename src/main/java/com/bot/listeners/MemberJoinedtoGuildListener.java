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
    public void onGuildMemberJoin(GuildMemberJoinEvent event)
    {
        Member member = event.getMember();
        User user = event.getUser();

        // Construir DTO o entidad
        UserDTO userDTO = UserDTO.builder()
                .discordId(user.getId())
                .serverId(event.getGuild().getId())
                .username(user.getName())
                .globalName(user.getGlobalName())
                .nickname(member.getNickname())
                .isBot(user.isBot())
                .isOwner(event.getGuild().getOwnerId().equals(user.getId()))
                .isAdmin(member.hasPermission(Permission.ADMINISTRATOR))
                .joinedAt(member.getTimeJoined().toInstant())
                .lastSync(null)
                .build();

        UserDTO savedUser = _adminService.save(userDTO)
                .orElseThrow(() -> new RuntimeException("Error saving user"));

        StringBuilder sb = new StringBuilder();

        sb.append("Nuevo miembro unido al servidor:\n");
        sb.append(savedUser.toString()).append("\n").append("========");

        System.out.println(sb.toString());
    }
}
