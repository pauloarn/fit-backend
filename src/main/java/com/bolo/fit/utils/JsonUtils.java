package com.bolo.fit.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class JsonUtils {

    @Getter
    private final ObjectMapper objectMapper;

    @Autowired
    public JsonUtils(Jackson2ObjectMapperBuilder mapperBuilder) {
        this.objectMapper = mapperBuilder.build();
    }

    public String objectToJson(Object obj) throws JsonProcessingException {
        return objectMapper.writeValueAsString(obj);
    }

    public <C> C jsonToObject(String json, Class<C> clazz) throws IOException {
        return objectMapper.readValue(json, clazz);
    }

    public <C> C jsonToObject(String json, TypeReference<C> typeReference) throws IOException {
        return objectMapper.readValue(json, typeReference);
    }
}
