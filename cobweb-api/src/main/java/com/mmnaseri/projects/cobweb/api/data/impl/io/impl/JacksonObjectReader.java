package com.mmnaseri.projects.cobweb.api.data.impl.io.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mmnaseri.projects.cobweb.api.data.impl.io.ObjectReader;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

/**
 * @author Mohammad Milad Naseri (mmnaseri@programmer.net)
 * @since 1.0 (7/12/17, 2:09 AM)
 */
public class JacksonObjectReader<O> implements ObjectReader<O> {

    private final ObjectMapper objectMapper;

    public JacksonObjectReader(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public <I extends O> I read(Path path, Class<I> type) throws IOException {
        return objectMapper.readValue(Files.newInputStream(path, StandardOpenOption.READ), type);
    }

}
