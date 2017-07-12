package com.mmnaseri.projects.cobweb.api.data.impl.nio.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mmnaseri.projects.cobweb.api.data.impl.nio.ObjectInputOutputManager;
import com.mmnaseri.projects.cobweb.api.data.impl.nio.ObjectReader;
import com.mmnaseri.projects.cobweb.api.data.impl.nio.ObjectWriter;

/**
 * @author Mohammad Milad Naseri (mmnaseri@programmer.net)
 * @since 1.0 (7/12/17, 2:11 AM)
 */
public class JacksonObjectInputOutputManager<O> implements ObjectInputOutputManager<O> {

    private final JacksonObjectReader<O> reader;
    private final JacksonObjectWriter<O> writer;

    public JacksonObjectInputOutputManager(ObjectMapper objectMapper) {
        reader = new JacksonObjectReader<>(objectMapper);
        writer = new JacksonObjectWriter<>(objectMapper);
    }

    @Override
    public ObjectReader<O> getObjectReader() {
        return reader;
    }

    @Override
    public ObjectWriter<O> getObjectWriter() {
        return writer;
    }

}
