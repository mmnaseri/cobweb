package com.mmnaseri.projects.cobweb.api.io.impl;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * @author Mohammad Milad Naseri (mmnaseri@programmer.net)
 * @since 1.0 (7/10/17, 6:32 PM)
 */
public class SerializableObjectReaderTest extends BaseObjectReaderTest {

    @Override
    protected SerializableObjectReader getObjectReader() {
        return new SerializableObjectReader();
    }

    @Override
    protected void write(Path path, String written) throws IOException {
        final ObjectOutputStream stream = new ObjectOutputStream(Files.newOutputStream(path));
        stream.writeObject(written);
    }

}