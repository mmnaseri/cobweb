package com.mmnaseri.projects.cobweb.api.data.impl.nio.impl;

import com.mmnaseri.projects.cobweb.api.data.impl.nio.ObjectInputOutputManager;
import com.mmnaseri.projects.cobweb.api.data.impl.nio.ObjectReader;
import com.mmnaseri.projects.cobweb.api.data.impl.nio.ObjectWriter;

import java.io.Serializable;

/**
 * @author Mohammad Milad Naseri (mmnaseri@programmer.net)
 * @since 1.0 (7/10/17, 6:43 PM)
 */
public class SerializableObjectInputOutputManager<I extends Serializable> implements ObjectInputOutputManager<I> {

    private final ObjectReader<I> objectReader = new SerializableObjectReader<>();
    private final ObjectWriter<I> objectWriter = new SerializableObjectWriter<>();

    @Override
    public ObjectReader<I> getObjectReader() {
        return objectReader;
    }

    @Override
    public ObjectWriter<I> getObjectWriter() {
        return objectWriter;
    }

}
