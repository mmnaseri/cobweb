package com.mmnaseri.projects.cobweb.api.data.ix;

import com.mmnaseri.projects.cobweb.api.data.Index;
import com.mmnaseri.projects.cobweb.domain.content.Persistent;

import java.io.Serializable;

/**
 * @author Mohammad Milad Naseri (mmnaseri@programmer.net)
 * @since 1.0 (7/13/17, 1:31 AM)
 */
public class PersistentIndexWrapper<K extends Serializable & Comparable<K>, P extends Persistent<K>> extends IndexWrapper<K, P> {

    public PersistentIndexWrapper(Index<K, P> index) {
        super(index);
    }

    public P save(P persistent) {
        return save(persistent.getId(), persistent);
    }

    public void delete(P persistent) {
        delete(persistent.getId());
    }

}
