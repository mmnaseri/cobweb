package com.mmnaseri.projects.cobweb.api.data.impl.io;

import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Path;

/**
 * @author Mohammad Milad Naseri (mmnaseri@programmer.net)
 * @since 1.0 (7/10/17, 6:05 PM)
 */
@FunctionalInterface
public interface ObjectWriter extends Serializable {

    void write(Path path, Object object) throws IOException;

}
