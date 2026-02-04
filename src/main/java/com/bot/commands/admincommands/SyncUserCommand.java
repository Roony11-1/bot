package com.bot.commands.admincommands;

import java.util.ArrayList;
import java.util.List;

import com.bot.admin.UserDTO;
import com.bot.admin.service.AdminService;
import com.bot.commands.ICommand;

import lombok.RequiredArgsConstructor;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

@RequiredArgsConstructor
public class SyncUserCommand implements ICommand
{
    private final String _ownerId;
    private final AdminService _adminService;

    @Override
    public void execute(String[] args, MessageReceivedEvent event) 
    {
        if (!event.isFromGuild())
        {
            System.out.println("El comando !sync solo puede ser usado en un servidor.");
            return;
        }

        if (!event.getAuthor().getId().equals(_ownerId))
        {
            System.out.println("Comando !sync denegado para el usuario: " + event.getAuthor().getName());
            return;
        }

        Guild server = event.getGuild();

        server.loadMembers()
            .onSuccess(members -> 
            {
                List<UserDTO> users = new ArrayList<>();

                logStart(server, members.size());

                members.forEach(member -> 
                {
                    UserDTO dto = mapToDTO(server, member);

                    users.add(dto);
                });

                List<UserDTO> savedUsers = _adminService.saveAll(users);

                savedUsers.forEach(user -> System.out.println(user.toString()));

                System.out.println("Total usuarios sincronizados: " + savedUsers.size());

                logEnd();
            })
            .onError(error -> 
            {
                logError(error);
            });
    }

    private UserDTO mapToDTO(Guild server, Member member) 
    {
        return UserDTO.builder()
            .discordId(member.getUser().getId())
            .serverId(server.getId())
            .isBot(member.getUser().isBot())
            .username(member.getUser().getName())
            .globalName(member.getUser().getGlobalName())
            .nickname(member.getNickname())
            .isOwner(server.getOwnerId().equals(member.getUser().getId()))
            .isAdmin(member.hasPermission(Permission.ADMINISTRATOR))
            .joinedAt(member.getTimeJoined().toInstant())
            .lastSync(null)
            .build();
    }

    private void logStart(Guild server, int total) 
    {
        System.out.println("========== INICIO SYNC DE USUARIOS ==========");
        System.out.println("Servidor: " + server.getName() + " (" + server.getId() + ")");
        System.out.println("Total miembros: " + total);
        System.out.println("============================================");
    }

    private void logEnd() 
    {
        System.out.println("=========== FIN SYNC DE USUARIOS ===========");
    }

    private void logError(Throwable error) 
    {
        System.out.println("Error al cargar los miembros del servidor: " + error.getMessage());
    }

    @Override
    public void help(MessageReceivedEvent event) 
    {

    }

    @Override
    public String name() 
    {
        return "!sync";
    }
}
