package com.mmnaseri.projects.cobweb.api.io.impl;

import com.mmnaseri.projects.cobweb.api.io.ObjectInputOutputManager;

import java.io.Serializable;

/**
 * @author Mohammad Milad Naseri (mmnaseri@programmer.net)
 * @since 1.0 (7/10/17, 6:47 PM)
 */
public class SerializableObjectInputOutputManagerTest extends BaseObjectInputOutputManagerTest {

    @Override
    protected ObjectInputOutputManager getManager() {
        return new SerializableObjectInputOutputManager();
    }

    @Override
    protected Serializable getSubject() {
        return "this is a test";
    }

}
