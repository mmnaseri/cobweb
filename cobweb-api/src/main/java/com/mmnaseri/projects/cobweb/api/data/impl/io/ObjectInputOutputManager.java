package com.mmnaseri.projects.cobweb.api.data.impl.io;

import java.io.Serializable;

/**
 * @author Mohammad Milad Naseri (mmnaseri@programmer.net)
 * @since 1.0 (7/10/17, 6:42 PM)
 */
public interface ObjectInputOutputManager extends Serializable {

    ObjectReader getObjectReader();

    ObjectWriter getObjectWriter();

}
