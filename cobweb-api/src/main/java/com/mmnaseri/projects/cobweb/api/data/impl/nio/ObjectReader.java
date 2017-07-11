package com.mmnaseri.projects.cobweb.api.data.impl.nio;

import com.mmnaseri.projects.cobweb.api.common.ParameterizedTypeReference;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.nio.file.Path;

/**
 * @author Mohammad Milad Naseri (mmnaseri@programmer.net)
 * @since 1.0 (7/10/17, 6:28 PM)
 */
@FunctionalInterface
public interface ObjectReader<O> {

    <I extends O> I read(Path path, Class<I> type) throws IOException;

    default <I extends O> I read(Path path, Type type) throws IOException {
        final Class<?> clazz;
        if (type instanceof Class<?>) {
            clazz = (Class) type;
        } else if (type instanceof ParameterizedType) {
            clazz = (Class<?>) ((ParameterizedType) type).getRawType();
        } else {
            throw new IOException("Invalid type for expected object: " + type);
        }
        //noinspection unchecked
        return read(path, (Class<I>) clazz);
    }

    default <I extends O> I read(Path path, ParameterizedTypeReference<I> typeReference) throws IOException {
        return read(path, typeReference.getType());
    }

}
