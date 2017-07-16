package com.mmnaseri.projects.cobweb.api.graph.impl;

import com.mmnaseri.projects.cobweb.api.io.ObjectInputOutputManagerFactory;
import com.mmnaseri.projects.cobweb.api.io.impl.JacksonObjectInputOutputManagerFactory;
import com.mmnaseri.projects.cobweb.api.io.impl.SerializableObjectInputOutputManagerFactory;

/**
 * @author Mohammad Milad Naseri (mmnaseri@programmer.net)
 * @since 1.0 (7/16/17, 1:39 AM)
 */
public abstract class ObjectInputOutputType {

    public abstract ObjectInputOutputManagerFactory factory();

    private ObjectInputOutputType() {
    }

    public static final ObjectInputOutputType JACKSON = new ObjectInputOutputType() {
        @Override
        public ObjectInputOutputManagerFactory factory() {
            return new JacksonObjectInputOutputManagerFactory();
        }
    };

    public static final ObjectInputOutputType BINARY = new ObjectInputOutputType() {
        @Override
        public ObjectInputOutputManagerFactory factory() {
            return new SerializableObjectInputOutputManagerFactory();
        }
    };

}
