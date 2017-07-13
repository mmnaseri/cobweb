package com.mmnaseri.projects.cobweb.api.common;

import java.util.Collection;

/**
 * @author Mohammad Milad Naseri (mmnaseri@programmer.net)
 * @since 1.0 (7/13/17, 8:52 AM)
 */
public interface Bag<K> {

    int size();

    boolean isEmpty();

    Collection<K> values();

}
