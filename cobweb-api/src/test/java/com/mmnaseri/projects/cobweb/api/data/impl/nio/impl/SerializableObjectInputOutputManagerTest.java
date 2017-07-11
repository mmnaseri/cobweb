package com.mmnaseri.projects.cobweb.api.data.impl.nio.impl;

import com.mmnaseri.projects.cobweb.api.data.impl.nio.ObjectInputOutputManager;

import java.io.Serializable;

/**
 * @author Mohammad Milad Naseri (mmnaseri@programmer.net)
 * @since 1.0 (7/10/17, 6:47 PM)
 */
public class SerializableObjectInputOutputManagerTest extends BaseObjectInputOutputManagerTest<Serializable> {

    @Override
    protected ObjectInputOutputManager<Serializable> getManager() {
        return new SerializableObjectInputOutputManager<>();
    }

    @Override
    protected Serializable getSubject() {
        return "this is a test";
    }

}
