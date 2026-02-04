package com.bot.service;

import java.io.IOException;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.github.cdimascio.dotenv.Dotenv;
import lombok.RequiredArgsConstructor;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

@RequiredArgsConstructor
public abstract class ApiService<T>
{
    protected static final Dotenv DOTENV = Dotenv.load();

    protected final OkHttpClient _clienteHttp;
    protected final ObjectMapper _mapper;

    protected final String scheme = "http";
    protected final String version = "v1";
    protected final String host = DOTENV.get("API_HOST");
    protected final int port = Integer.parseInt(DOTENV.get("API_PORT"));

    protected HttpUrl.Builder baseUrl()
    {
        return new HttpUrl.Builder()
            .scheme(scheme)
            .host(host)
            .port(port)
            .addPathSegment(resource())
            .addPathSegment(version);
    }

    protected abstract String resource();

    protected <R> R read(Response response, TypeReference<R> type) throws IOException
    {
        String body = response.body().string();

        return _mapper
            .readValue(
                body, 
                type);
    }

    protected Response post(HttpUrl httpUrl) throws IOException
    {
        return post(httpUrl, RequestBody.create(new byte[0]));
    }

    protected Response post(HttpUrl httpUrl, RequestBody requestBody) throws IOException
    {
        Request request = new Request.Builder()
            .url(httpUrl)
            .post(requestBody)
            .build();

        return _clienteHttp.newCall(request).execute();
    }

    protected Response delete(HttpUrl httpUrl) throws IOException
    {
        Request requestDelete = new Request.Builder()
            .url(httpUrl)
            .delete()
            .build();

        return _clienteHttp.newCall(requestDelete).execute();
    }

    protected Response get(HttpUrl httpUrl) throws IOException
    {
        Request request = new Request.Builder()
            .url(httpUrl)
            .get()
            .build();

        return _clienteHttp.newCall(request).execute();
    }
}
