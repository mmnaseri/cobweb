package com.mmnaseri.projects.cobweb.api.common;

import com.mmnaseri.projects.cobweb.api.data.model.SerializableDocument;

import java.io.Serializable;
import java.util.UUID;

/**
 * @author Mohammad Milad Naseri (mmnaseri@programmer.net)
 * @since 1.0 (7/12/17, 11:58 PM)
 */
public class SerializableSetTest extends BaseSerializableTypeTest {

    @Override
    protected Serializable getObject() {
        final SerializableSet<Serializable> set = new SerializableSet<>();
        for (int i = 0; i < 100; i++) {
            set.add(new SerializableDocument(UUID.randomUUID()));
        }
        return set;
    }

}