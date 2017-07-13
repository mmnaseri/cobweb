package com.mmnaseri.projects.cobweb.api.io.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mmnaseri.projects.cobweb.api.io.ObjectInputOutputManager;
import com.mmnaseri.projects.cobweb.api.io.ObjectReader;
import com.mmnaseri.projects.cobweb.api.io.ObjectWriter;

/**
 * @author Mohammad Milad Naseri (mmnaseri@programmer.net)
 * @since 1.0 (7/12/17, 2:11 AM)
 */
public class JacksonObjectInputOutputManager implements ObjectInputOutputManager {

    private static final long serialVersionUID = 3553771152200602518L;
    private final JacksonObjectReader reader;
    private final JacksonObjectWriter writer;

    public JacksonObjectInputOutputManager(ObjectMapper objectMapper) {
        reader = new JacksonObjectReader(objectMapper);
        writer = new JacksonObjectWriter(objectMapper);
    }

    @Override
    public ObjectReader getObjectReader() {
        return reader;
    }

    @Override
    public ObjectWriter getObjectWriter() {
        return writer;
    }

}
