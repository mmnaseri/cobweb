package com.mmnaseri.projects.cobweb.api.data.impl.io.impl;

import com.mmnaseri.projects.cobweb.api.data.impl.io.ObjectReader;

import java.io.*;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * @author Mohammad Milad Naseri (mmnaseri@programmer.net)
 * @since 1.0 (7/10/17, 5:32 PM)
 */
class BinaryObjectReader implements ObjectReader {

    @Override
    public <I extends Serializable> I read(File file, Type type) throws IOException {
        final Class<?> clazz;
        if (type instanceof Class<?>) {
            clazz = (Class) type;
        } else if (type instanceof ParameterizedType) {
            clazz = (Class<?>) ((ParameterizedType) type).getRawType();
        } else {
            throw new IllegalArgumentException();
        }
        try (
                final FileInputStream fileInputStream = new FileInputStream(file);
                final ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
        ) {
            try {
                final Object object = objectInputStream.readObject();
                if (!(clazz.isInstance(object))) {
                    throw new IllegalStateException();
                }
                //noinspection unchecked
                return (I) object;
            } catch (ClassNotFoundException e) {
                throw new IOException("Invalid data type", e);
            }
        }
    }

}
