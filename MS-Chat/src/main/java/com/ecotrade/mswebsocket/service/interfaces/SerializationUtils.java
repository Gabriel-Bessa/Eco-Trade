package com.ecotrade.mswebsocket.service.interfaces;

import com.ecotrade.mswebsocket.config.errors.BusinessException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public interface SerializationUtils {

    default <T> String serialize(T value) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(value);
        } catch (JsonProcessingException e) {
            throw new BusinessException("redis.exception", "redis.unspected.error");
        }
    }

    default <T> T deserializar(String value, Class<T> classTarget) {
        if (value ==  null) {
            return null;
        }
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(value, classTarget);
        } catch (JsonProcessingException e) {
            throw new BusinessException("redis.exception", "redis.unspected.error");
        }
    }
}
