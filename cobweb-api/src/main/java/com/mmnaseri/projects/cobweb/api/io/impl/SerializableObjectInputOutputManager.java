package com.mmnaseri.projects.cobweb.api.io.impl;

import com.mmnaseri.projects.cobweb.api.io.ObjectInputOutputManager;
import com.mmnaseri.projects.cobweb.api.io.ObjectReader;
import com.mmnaseri.projects.cobweb.api.io.ObjectWriter;

/**
 * @author Mohammad Milad Naseri (mmnaseri@programmer.net)
 * @since 1.0 (7/10/17, 6:43 PM)
 */
public class SerializableObjectInputOutputManager implements ObjectInputOutputManager {

    private static final long serialVersionUID = 834336052619626604L;
    private final ObjectReader objectReader = new SerializableObjectReader();
    private final ObjectWriter objectWriter = new SerializableObjectWriter();

    @Override
    public ObjectReader getObjectReader() {
        return objectReader;
    }

    @Override
    public ObjectWriter getObjectWriter() {
        return objectWriter;
    }

}
