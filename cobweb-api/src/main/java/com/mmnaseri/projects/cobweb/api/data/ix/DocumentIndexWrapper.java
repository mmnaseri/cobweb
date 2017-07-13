package com.mmnaseri.projects.cobweb.api.data.ix;

import com.mmnaseri.projects.cobweb.api.data.Index;
import com.mmnaseri.projects.cobweb.domain.content.Document;
import com.mmnaseri.projects.cobweb.domain.content.DocumentProperties;
import com.mmnaseri.projects.cobweb.domain.content.Persistent;

import java.io.Serializable;

/**
 * @author Mohammad Milad Naseri (mmnaseri@programmer.net)
 * @since 1.0 (7/13/17, 12:54 AM)
 */
public class DocumentIndexWrapper<K extends Serializable & Comparable<K>, D extends Document<K>> extends IndexWrapper<K, DocumentProperties> {

    public DocumentIndexWrapper(Index<K, DocumentProperties> index) {
        super(index);
    }

    public D save(D document) {
        save(document.getId(), document.getProperties());
        return document;
    }

    public void delete(Persistent<K> persistent) {
        delete(persistent.getId());
    }

}
