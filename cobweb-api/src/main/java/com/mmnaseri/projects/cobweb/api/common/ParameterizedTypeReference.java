package com.mmnaseri.projects.cobweb.api.common;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * @author Mohammad Milad Naseri (mmnaseri@programmer.net)
 * @since 1.0 (7/9/17, 3:18 PM)
 */
public abstract class ParameterizedTypeReference<T> {

    private final Type type;

    public ParameterizedTypeReference() {
        type = ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    public Type getType() {
        return type;
    }

}
