package com.bot.service;

import java.util.concurrent.TimeUnit;

import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;

public class MemberRoleService 
{
    public void assignDefaultRole(GuildMemberJoinEvent event)
    {
        Role rolUsuario = event.getGuild()
            .getRolesByName("usuario", true)
            .stream()
            .findFirst()
            .orElseThrow(() -> new RuntimeException(
                "No se encontró el rol 'usuario' en el servidor: " + event.getGuild().getId()
            ));

        event.getGuild()
            .addRoleToMember(event.getMember(), rolUsuario)
            .queueAfter(
                2, TimeUnit.SECONDS,
                success -> System.out.println("Rol 'usuario' asignado a " + event.getUser().getName()),
                error -> System.out.println("Error al asignar rol: " + error.getMessage())
            );
    }
}
