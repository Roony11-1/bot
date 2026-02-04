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

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Response;

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
    
    public Optional<Unidad> createUnit(String discordId, String name, int level)
    {
        try 
        {
            HttpUrl httpUrl = baseUrl()
                .addPathSegment("discord")
                .addQueryParameter("discordId", discordId)
                .addQueryParameter("nombre", name)
                .addQueryParameter("nivel", String.valueOf(level))
                .build();

            try (Response response = post(httpUrl)) 
            {
                if (!response.isSuccessful())
                    return Optional.empty();

                return Optional.of(read(response, new TypeReference<Unidad>() {}));
            }
        } 
        catch (IOException e) 
        {
            e.printStackTrace();
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
                    return Collections.emptyList();

                return read(response, new TypeReference<List<Unidad>>() {});
            }
        } 
        catch (IOException e) 
        {
            e.printStackTrace();
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
                    return Optional.empty();

                return Optional.of(
                    Long.parseLong(response.body().string().trim()));
            }
        } 
        catch (Exception e) 
        {
            e.printStackTrace();
            return Optional.empty();
        }
    }
}
