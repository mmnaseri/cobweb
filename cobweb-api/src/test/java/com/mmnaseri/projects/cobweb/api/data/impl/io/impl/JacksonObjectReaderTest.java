package com.mmnaseri.projects.cobweb.api.data.impl.io.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mmnaseri.projects.cobweb.api.data.impl.io.ObjectReader;
import org.testng.annotations.BeforeMethod;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * @author Mohammad Milad Naseri (mmnaseri@programmer.net)
 * @since 1.0 (7/12/17, 6:04 PM)
 */
public class JacksonObjectReaderTest extends BaseObjectReaderTest {

    private ObjectMapper objectMapper;

    @BeforeMethod
    public void setUp() throws Exception {
        super.setUp();
        objectMapper = new ObjectMapper();
    }

    @Override
    protected ObjectReader<String> getObjectReader() {
        return new JacksonObjectReader<>(objectMapper);
    }

    @Override
    protected void write(Path path, String written) throws IOException {
        objectMapper.writeValue(Files.newOutputStream(path), written);
    }

}