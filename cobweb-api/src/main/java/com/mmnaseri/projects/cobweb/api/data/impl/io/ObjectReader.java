package com.mmnaseri.projects.cobweb.api.data.impl.io;

import com.mmnaseri.projects.cobweb.api.common.ParameterizedTypeReference;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.Type;

/**
 * @author Mohammad Milad Naseri (mmnaseri@programmer.net)
 * @since 1.0 (7/8/17, 8:11 PM)
 */
@FunctionalInterface
public interface ObjectReader {

    default <I extends Serializable> I read(File file, Class<I> type) throws IOException {
        return read(file, new ParameterizedTypeReference<I>() {
        });
    }

    default <I extends Serializable> I read(File file, ParameterizedTypeReference<I> typeReference) throws IOException {
        return read(file, typeReference.getType());
    }

    <I extends Serializable> I read(File file, Type type) throws IOException;

}

