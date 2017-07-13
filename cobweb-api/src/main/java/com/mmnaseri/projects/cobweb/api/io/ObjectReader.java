package com.mmnaseri.projects.cobweb.api.io;

import com.mmnaseri.projects.cobweb.api.common.ParameterizedTypeReference;

import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.AnnotatedType;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.nio.file.Path;

/**
 * @author Mohammad Milad Naseri (mmnaseri@programmer.net)
 * @since 1.0 (7/10/17, 6:28 PM)
 */
@FunctionalInterface
public interface ObjectReader extends Serializable {

    <I> I read(Path path, Class<I> type) throws IOException;

    default <I> I read(Path path, Type type) throws IOException {
        final Class<?> clazz;
        if (type instanceof Class<?>) {
            clazz = (Class<?>) type;
        } else if (type instanceof ParameterizedType) {
            clazz = (Class<?>) ((ParameterizedType) type).getRawType();
        } else if (type instanceof TypeVariable) {
            final AnnotatedType[] bounds = ((TypeVariable) type).getAnnotatedBounds();
            clazz = (Class<?>) bounds[0].getType();
        } else {
            throw new IOException("Invalid type for expected object: " + type);
        }
        //noinspection unchecked
        return read(path, (Class<I>) clazz);
    }

    default <I> I read(Path path, ParameterizedTypeReference<I> typeReference) throws IOException {
        return read(path, typeReference.getType());
    }

}
