package com.mmnaseri.projects.cobweb.api.graph;

import java.io.Serializable;

/**
 * @author Mohammad Milad Naseri (mmnaseri@programmer.net)
 * @since 1.0 (7/12/17, 7:41 PM)
 */
public interface IdentifierGenerator<I extends Serializable & Comparable<I>> {

    I next();

}
