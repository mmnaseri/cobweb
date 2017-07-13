package com.mmnaseri.projects.cobweb.api.io.impl;

import com.mmnaseri.projects.cobweb.api.io.ObjectWriter;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

/**
 * @author Mohammad Milad Naseri (mmnaseri@programmer.net)
 * @since 1.0 (7/10/17, 6:07 PM)
 */
public class SerializableObjectWriter implements ObjectWriter {

    private static final long serialVersionUID = -608136591289615310L;

    @Override
    public void write(Path path, Object object) throws IOException {
        try (
                OutputStream outputStream = Files.newOutputStream(path, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.WRITE);
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
        ) {
            objectOutputStream.writeObject(object);
        }
    }

}
