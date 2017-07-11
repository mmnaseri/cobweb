package com.mmnaseri.projects.cobweb.api.data.impl.io.impl;

import com.mmnaseri.projects.cobweb.api.data.impl.io.ObjectWriter;

import java.io.*;

/**
 * @author Mohammad Milad Naseri (mmnaseri@programmer.net)
 * @since 1.0 (7/10/17, 5:31 PM)
 */
class BinaryObjectWriter implements ObjectWriter {

    @Override
    public void write(File file, Serializable object) throws IOException {
        try (
                final FileOutputStream fileOutputStream = new FileOutputStream(file);
                final ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
        ) {
            objectOutputStream.writeObject(object);
        }
    }

}
