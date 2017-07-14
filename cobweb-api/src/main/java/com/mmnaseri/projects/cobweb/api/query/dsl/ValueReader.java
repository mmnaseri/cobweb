package com.mmnaseri.projects.cobweb.api.query.dsl;

/**
 * @author Mohammad Milad Naseri (mmnaseri@programmer.net)
 * @since 1.0 (7/14/17, 3:14 PM)
 */
public interface ValueReader<E, S> {

    S read(E object);

}
