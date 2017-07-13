package com.mmnaseri.projects.cobweb.api.data;

import java.io.Serializable;

/**
 * @author Mohammad Milad Naseri (mmnaseri@programmer.net)
 * @since 1.0 (7/12/17, 5:30 PM)
 */
public interface Stringifier<E extends Serializable & Comparable<E>> extends Serializable {

    E fromString(String string);

    String toString(E value);

}
