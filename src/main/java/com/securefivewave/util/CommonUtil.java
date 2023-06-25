package com.securefivewave.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CommonUtil {

    public static String writeValueAsString(Object source) {
        final ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsString(source);
        }
        catch (JsonProcessingException ex) {
            log.error(ex.toString());
        }
        return null;
    }

    public static <T> T readValueFromString(String source, Class<T> clazz) {
        final ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(source, clazz);
        }
        catch (JsonProcessingException ex) {
            log.error(ex.toString());
        }
        return null;
    }

    public static <T> T readValueFromString(String source, TypeReference<T> ref) {
        final ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(source, ref);
        }
        catch (JsonProcessingException ex) {
            log.error(ex.toString());
        }
        return null;
    }

}