package com.bot.admin;

import java.time.Instant;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO 
{
    private String id;
    private String discordId;
    private String serverId;
    private boolean isBot;

    private String username;
    private String globalName;
    private String nickname;

    private boolean isOwner;
    private boolean isAdmin;

    private Instant joinedAt;
    private Instant lastSync;

    @Override
    public String toString() 
    {
        StringBuilder sb = new StringBuilder();

        sb.append("\n--------------------------------------------\n")
                .append("Usuario\n")
                .append("--------------------------------------------\n")
                .append("Id                :").append(id).append("\n")
                .append("Discord ID        : ").append(discordId).append("\n")
                .append("Server ID         : ").append(serverId).append("\n")
                .append("Username          : ").append(username).append("\n")
                .append("Global Name       : ").append(globalName != null ? globalName : "N/A").append("\n")
                .append("Nickname          : ").append(nickname != null ? nickname : "N/A").append("\n")
                .append("Es Bot            : ").append(isBot ? "Sí" : "No").append("\n")
                .append("Es Owner          : ").append(isOwner ? "Sí" : "No").append("\n")
                .append("Es Admin          : ").append(isAdmin ? "Sí" : "No").append("\n")
                .append("Fecha de unión    : ").append(joinedAt).append("\n")
                .append("Última sync       : ").append(lastSync).append("\n");

        return sb.toString();
    }
}
