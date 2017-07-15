package com.mmnaseri.projects.cobweb.api.data.model;

import com.mmnaseri.projects.cobweb.domain.content.Persistent;
import com.mmnaseri.projects.cobweb.domain.id.IdentifierFactory;

import java.io.Serializable;
import java.util.UUID;

/**
 * @author Mohammad Milad Naseri (mmnaseri@programmer.net)
 * @since 1.0 (7/10/17, 5:33 PM)
 */
public class SerializableDocument extends Persistent<UUID> implements Serializable {

    private static final long serialVersionUID = 385266305922864568L;
    private final UUID key;

    public SerializableDocument(UUID key) {
        this.key = key;
        setId(IdentifierFactory.UUID_IDENTIFIER_FACTORY.getIdentifier(key));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SerializableDocument that = (SerializableDocument) o;
        return key.equals(that.key);
    }

    @Override
    public int hashCode() {
        return key.hashCode();
    }

}
