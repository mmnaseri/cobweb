package com.mmnaseri.projects.cobweb.api.io.impl;

import com.mmnaseri.projects.cobweb.api.io.ObjectInputOutputManager;
import com.mmnaseri.projects.cobweb.api.io.ObjectInputOutputManagerFactory;

/**
 * @author Mohammad Milad Naseri (mmnaseri@programmer.net)
 * @since 1.0 (7/16/17, 1:38 AM)
 */
public class SerializableObjectInputOutputManagerFactory implements ObjectInputOutputManagerFactory {

    @Override
    public ObjectInputOutputManager getInstance() {
        return new SerializableObjectInputOutputManager();
    }

}
