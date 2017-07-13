package com.mmnaseri.projects.cobweb.api.io.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mmnaseri.projects.cobweb.api.io.ObjectWriter;
import org.testng.annotations.BeforeMethod;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * @author Mohammad Milad Naseri (mmnaseri@programmer.net)
 * @since 1.0 (7/12/17, 6:07 PM)
 */
public class JacksonObjectWriterTest extends BaseObjectWriterTest {

    private ObjectMapper objectMapper;

    @BeforeMethod
    public void setUp() throws Exception {
        super.setUp();
        objectMapper = new ObjectMapper();
    }

    @Override
    protected ObjectWriter getObjectWriter() {
        return new JacksonObjectWriter(objectMapper);
    }

    @Override
    protected Object read(Path path) throws IOException, ClassNotFoundException {
        return objectMapper.readValue(Files.newInputStream(path), String.class);
    }

}