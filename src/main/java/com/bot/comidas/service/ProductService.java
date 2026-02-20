package com.bot.comidas.service;

import java.util.Optional;

import com.bot.comidas.model.Product;
import com.bot.service.ApiService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ProductService extends ApiService<Product>
{
    public ProductService()
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
        return "product";
    }

    public Optional<Product> saveProduct(Product product)
    {
        try 
        {
            String json = _mapper.writeValueAsString(product);

            RequestBody body = RequestBody.create(
                    json,
                    MediaType.parse("application/json")
            );

            HttpUrl httpUrl = baseUrl()
                .build();

            try (Response response = post(httpUrl, body)) 
            {

                if (!response.isSuccessful()) 
                {
                    System.out.println("Error HTTP: " + response.code());
                    return Optional.empty();
                }
                
                return Optional.of(read(response, new TypeReference<Product>() {}));
            }
        } 
        catch (Exception e) 
        {
            e.printStackTrace();
            return Optional.empty();
        }
    } 
}
