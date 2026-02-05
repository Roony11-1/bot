package com.bot.admin.service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import com.bot.admin.UserDTO;
import com.bot.service.ApiService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import lombok.extern.slf4j.Slf4j;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.Response;

@Slf4j
public class AdminService extends ApiService<UserDTO>
{
    public AdminService()
    {
        super(
            new OkHttpClient(),
            new ObjectMapper()
                .configure(
                    DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,
                    false)
                .registerModule(new JavaTimeModule())
                .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
        );
    }

    @Override
    protected String resource() 
    {
        return "users";
    }

    public Optional<UserDTO> findByDiscordIdAndServerId(String discordId, String serverId)
    {
        try 
        {
            HttpUrl httpUrl = baseUrl()
                .addQueryParameter("discordId", discordId)
                .addQueryParameter("serverId", serverId)
                .build();

            try (Response response = get(httpUrl))
            {
                if (!response.isSuccessful()) 
                {
                    log.warn("Error al buscar usuario: {}", response.code());

                    return Optional.empty();
                }

                UserDTO user = read(response, new TypeReference<UserDTO>() {});

                return Optional.ofNullable(user);
            }
        } 
        catch (Exception e) 
        {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    public Optional<UserDTO> updateMessageStats(String discordId, String serverId, Instant lastMessageAt)
    {
        UserDTO user = findByDiscordIdAndServerId(discordId, serverId)
            .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

            user.setLastMenssageAt(lastMessageAt);
            user.setMessageCount(user.getMessageCount() + 1);

        UserDTO updatedUser = save(user).orElseThrow(() -> new RuntimeException("Error al actualizar usuario"));

        return Optional.of(updatedUser);
    }

    public Optional<UserDTO> save(UserDTO user) 
    {
        try 
        {
            HttpUrl httpUrl = baseUrl().build();

            String json = _mapper.writeValueAsString(user);
            RequestBody requestBody = RequestBody.create(json, okhttp3.MediaType.get("application/json"));

            try (Response response = post(httpUrl, requestBody)) 
            {
                if (!response.isSuccessful()) 
                {
                    log.error("Error al guardar usuario: {}", response.code());
                    return Optional.empty();
                }

                UserDTO savedUser = read(response, new TypeReference<UserDTO>() {});

                return Optional.ofNullable(savedUser);
            }
        } 
        catch (Exception e) 
        {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    public List<UserDTO> saveAll(List<UserDTO> users)
    {
        try 
        {
            HttpUrl httpUrl = baseUrl()
                .addPathSegment("bulk")
                .build();

            String json = _mapper.writeValueAsString(users);

            RequestBody requestBody = RequestBody.create(json, okhttp3.MediaType.get("application/json"));

            try (okhttp3.Response response = post(httpUrl, requestBody))
            {
                if (!response.isSuccessful())
                {
                    log.error("Error al guardar usuarios en bulk: {}", response.code());
                    return List.of();
                }

                return read(response, new TypeReference<List<UserDTO>>() {});
            } 
        } 
        catch (Exception e) 
        {
            e.printStackTrace();
            return List.of();
        }
    }
}
