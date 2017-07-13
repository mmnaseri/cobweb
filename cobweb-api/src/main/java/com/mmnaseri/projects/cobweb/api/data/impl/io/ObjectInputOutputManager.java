package com.mmnaseri.projects.cobweb.api.data.impl.io;

/**
 * @author Mohammad Milad Naseri (mmnaseri@programmer.net)
 * @since 1.0 (7/10/17, 6:42 PM)
 */
public interface ObjectInputOutputManager<O> {

    ObjectReader<O> getObjectReader();

    ObjectWriter<O> getObjectWriter();

}
