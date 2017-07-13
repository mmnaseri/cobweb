package com.mmnaseri.projects.cobweb.api.common;

import java.io.Serializable;
import java.util.UUID;

/**
 * @author Mohammad Milad Naseri (mmnaseri@programmer.net)
 * @since 1.0 (7/12/17, 11:54 PM)
 */
public class SerializableMapTest extends BaseSerializableTypeTest {

    @Override
    protected Serializable getObject() {
        final SerializableMap<String, String> map = new SerializableMap<>();
        for (int i = 0; i < 100; i++) {
            map.put(UUID.randomUUID().toString(), UUID.randomUUID().toString());
        }
        return map;
    }

}