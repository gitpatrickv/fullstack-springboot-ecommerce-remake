package com.ecommerce.ecommerce_remake.common.dto;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Model {
    private static final Logger log = LoggerFactory.getLogger(Model.class);

    public static Model parse(String jsonStr, Class c) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());
            return (Model)mapper.readerFor(c).readValue(jsonStr);
        } catch (JsonProcessingException e) {
            log.error(e.getMessage());
            throw new IllegalArgumentException(e.getOriginalMessage() + jsonStr);
        }
    }
    public Model() {
    }

}
