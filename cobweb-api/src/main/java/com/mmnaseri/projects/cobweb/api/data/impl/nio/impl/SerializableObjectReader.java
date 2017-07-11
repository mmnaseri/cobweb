package com.mmnaseri.projects.cobweb.api.data.impl.nio.impl;

import com.mmnaseri.projects.cobweb.api.data.impl.nio.ObjectReader;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

/**
 * @author Mohammad Milad Naseri (mmnaseri@programmer.net)
 * @since 1.0 (7/10/17, 6:30 PM)
 */
public class SerializableObjectReader<O extends Serializable> implements ObjectReader<O> {

    @Override
    public <I extends O> I read(Path path, Class<I> type) throws IOException {
        final Object object;
        try (final ObjectInputStream stream = new ObjectInputStream(Files.newInputStream(path, StandardOpenOption.READ))) {
            try {
                object = stream.readObject();
            } catch (ClassNotFoundException e) {
                throw new IOException("Unknown object type found in stream", e);
            }
        }
        if (!type.isInstance(object)) {
            throw new IOException("Expected object of type " + type + " but found " + object);
        }
        //noinspection unchecked
        return (I) object;
    }

}
