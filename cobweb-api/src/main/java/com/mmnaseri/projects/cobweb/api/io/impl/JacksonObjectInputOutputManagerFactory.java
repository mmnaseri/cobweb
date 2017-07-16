package com.mmnaseri.projects.cobweb.api.io.impl;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mmnaseri.projects.cobweb.api.io.ObjectInputOutputManager;
import com.mmnaseri.projects.cobweb.api.io.ObjectInputOutputManagerFactory;

/**
 * @author Mohammad Milad Naseri (mmnaseri@programmer.net)
 * @since 1.0 (7/16/17, 1:37 AM)
 */
public class JacksonObjectInputOutputManagerFactory implements ObjectInputOutputManagerFactory {

    @Override
    public ObjectInputOutputManager getInstance() {
        final ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.addMixIn(Object.class, ClassInfoMixin.class);
        return new JacksonObjectInputOutputManager(objectMapper);
    }

    @JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, property = "__class")
    private final class ClassInfoMixin {
    }

}
