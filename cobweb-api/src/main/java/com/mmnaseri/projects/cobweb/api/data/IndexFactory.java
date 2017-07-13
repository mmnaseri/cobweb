package com.mmnaseri.projects.cobweb.api.data;

import java.io.Serializable;

/**
 * @author Mohammad Milad Naseri (mmnaseri@programmer.net)
 * @since 1.0 (7/8/17, 8:02 PM)
 */
public interface IndexFactory<C extends IndexConfiguration> extends Serializable {

    <I extends Serializable & Comparable<I>, V extends Serializable> Index<I, V> getInstance(C configuration);

}
