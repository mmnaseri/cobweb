package com.mmnaseri.projects.cobweb.api.data.impl.nio;

import java.io.IOException;
import java.nio.file.Path;

/**
 * @author Mohammad Milad Naseri (mmnaseri@programmer.net)
 * @since 1.0 (7/10/17, 6:05 PM)
 */
@FunctionalInterface
public interface ObjectWriter<O> {

    void write(Path path, O object) throws IOException;

}
