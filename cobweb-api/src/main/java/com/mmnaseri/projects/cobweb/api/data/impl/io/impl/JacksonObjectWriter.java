package com.mmnaseri.projects.cobweb.api.data.impl.io.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mmnaseri.projects.cobweb.api.data.impl.io.ObjectWriter;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

/**
 * @author Mohammad Milad Naseri (mmnaseri@programmer.net)
 * @since 1.0 (7/12/17, 2:08 AM)
 */
public class JacksonObjectWriter<O> implements ObjectWriter<O> {

    private final ObjectMapper objectMapper;

    public JacksonObjectWriter(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public void write(Path path, O object) throws IOException {
        objectMapper.writer().writeValue(Files.newOutputStream(path, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.WRITE), object);
    }

}
