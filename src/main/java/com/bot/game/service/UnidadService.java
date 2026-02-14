package com.bot.game.service;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import com.bot.game.model.Unidad;
import com.bot.service.ApiService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Response;

@Slf4j
public class UnidadService extends ApiService<Unidad>
{
    public UnidadService()
    {
        super(
            new OkHttpClient(),
            new ObjectMapper()
                .configure(
                    DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,
                    false)
        );
    }

    @Override
    protected String resource()
    {
        return "unidad";
    }
    
    public Optional<Unidad> createUnit(String discordId, String name)
    {
        try 
        {
            HttpUrl httpUrl = baseUrl()
                .addPathSegment("discord")
                .addQueryParameter("discordId", discordId)
                .addQueryParameter("nombre", name)
                .build();

            try (Response response = post(httpUrl)) 
            {
                if (!response.isSuccessful())
                {
                    log.warn("Error al crear unidad: {}", response.code());
                    return Optional.empty();
                }

                return Optional.of(read(response, new TypeReference<Unidad>() {}));
            }
        } 
        catch (IOException e) 
        {
            log.error("Error al crear unidad", e);
            return Optional.empty();
        }
    }

    public List<Unidad> findByDiscordId(String discordId)
    {
        try 
        {
            HttpUrl urlGet = baseUrl()
                .addQueryParameter("discordId", discordId)
                .build();
            
            try (Response response = get(urlGet)) 
            {
                if (!response.isSuccessful())
                {
                    log.warn("Error al obtener unidades: {}", response.code());
                    return Collections.emptyList();
                }

                return read(response, new TypeReference<List<Unidad>>() {});
            }
        } 
        catch (IOException e) 
        {
            log.error("Error al obtener unidades", e);
            return Collections.emptyList();
        }
    }

    public Optional<Long> deleteByDiscordId(String discordId)
    {
        try 
        {
            HttpUrl urlDelete = baseUrl()
                .addPathSegment("discord")
                .addQueryParameter("discordId", discordId)
                .build();

            try (Response response = delete(urlDelete))
            {
                if (!response.isSuccessful())
                {
                    log.warn("Error al eliminar unidades: {}", response.code());
                    return Optional.empty();
                }

                return Optional.of(
                    Long.parseLong(response.body().string().trim()));
            }
        } 
        catch (Exception e) 
        {
            log.error("Error al eliminar unidades", e);
            return Optional.empty();
        }
    }
}
