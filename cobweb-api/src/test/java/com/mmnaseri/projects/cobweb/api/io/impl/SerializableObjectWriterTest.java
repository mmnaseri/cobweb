package com.mmnaseri.projects.cobweb.api.io.impl;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

/**
 * @author Mohammad Milad Naseri (mmnaseri@programmer.net)
 * @since 1.0 (7/10/17, 6:09 PM)
 */
public class SerializableObjectWriterTest extends BaseObjectWriterTest {

    @Override
    protected SerializableObjectWriter getObjectWriter() {
        return new SerializableObjectWriter();
    }

    @Override
    protected Object read(Path path) throws IOException, ClassNotFoundException {
        final Object read;
        try (
                final ObjectInputStream stream = new ObjectInputStream(Files.newInputStream(path, StandardOpenOption.READ))
        ) {
            read = stream.readObject();
        }
        return read;
    }

}