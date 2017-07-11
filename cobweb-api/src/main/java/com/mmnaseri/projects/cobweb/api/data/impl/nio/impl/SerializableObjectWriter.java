package com.mmnaseri.projects.cobweb.api.data.impl.nio.impl;

import com.mmnaseri.projects.cobweb.api.data.impl.nio.ObjectWriter;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

/**
 * @author Mohammad Milad Naseri (mmnaseri@programmer.net)
 * @since 1.0 (7/10/17, 6:07 PM)
 */
public class SerializableObjectWriter<O extends Serializable> implements ObjectWriter<O> {

    @Override
    public void write(Path path, O object) throws IOException {
        try (
                final OutputStream outputStream = Files.newOutputStream(path, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.WRITE);
                final ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
        ) {
            objectOutputStream.writeObject(object);
        }
    }

}
