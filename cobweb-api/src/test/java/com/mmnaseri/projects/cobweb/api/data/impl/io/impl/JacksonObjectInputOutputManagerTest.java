package com.mmnaseri.projects.cobweb.api.data.impl.io.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mmnaseri.projects.cobweb.api.data.impl.io.ObjectInputOutputManager;

import java.util.UUID;

/**
 * @author Mohammad Milad Naseri (mmnaseri@programmer.net)
 * @since 1.0 (7/12/17, 2:14 AM)
 */
public class JacksonObjectInputOutputManagerTest extends BaseObjectInputOutputManagerTest {

    @Override
    protected ObjectInputOutputManager getManager() {
        return new JacksonObjectInputOutputManager(new ObjectMapper());
    }

    @Override
    protected Object getSubject() {
        return UUID.randomUUID();
    }

}