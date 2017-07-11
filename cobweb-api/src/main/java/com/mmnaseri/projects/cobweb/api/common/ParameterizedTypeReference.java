package com.mmnaseri.projects.cobweb.api.common;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * @author Mohammad Milad Naseri (mmnaseri@programmer.net)
 * @since 1.0 (7/9/17, 3:18 PM)
 */
public abstract class ParameterizedTypeReference<T> {

    private final Type type;

    protected ParameterizedTypeReference() {
        Class<?> parameterizedTypeReferenceSubclass = findParameterizedTypeReferenceSubclass(getClass());
        Type type = parameterizedTypeReferenceSubclass.getGenericSuperclass();
        if (!(type instanceof ParameterizedType)) {
            throw new IllegalStateException("Type must be a parameterized type");
        }
        ParameterizedType parameterizedType = (ParameterizedType) type;
        if (parameterizedType.getActualTypeArguments().length != 1) {
            throw new IllegalStateException("Number of type arguments must be 1");
        }
        this.type = parameterizedType.getActualTypeArguments()[0];
    }


    public Type getType() {
        return this.type;
    }

    @Override
    public boolean equals(Object obj) {
        return (this == obj || (obj instanceof ParameterizedTypeReference &&
                this.type.equals(((ParameterizedTypeReference<?>) obj).type)));
    }

    @Override
    public int hashCode() {
        return this.type.hashCode();
    }

    @Override
    public String toString() {
        return "ParameterizedTypeReference<" + this.type + ">";
    }


    private static Class<?> findParameterizedTypeReferenceSubclass(Class<?> child) {
        Class<?> parent = child.getSuperclass();
        if (Object.class == parent) {
            throw new IllegalStateException("Expected ParameterizedTypeReference superclass");
        }
        else if (ParameterizedTypeReference.class == parent) {
            return child;
        }
        else {
            return findParameterizedTypeReferenceSubclass(parent);
        }
    }

}
