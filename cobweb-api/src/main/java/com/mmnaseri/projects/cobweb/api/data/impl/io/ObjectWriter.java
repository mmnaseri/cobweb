package com.mmnaseri.projects.cobweb.api.data.impl.io;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;

/**
 * @author Mohammad Milad Naseri (mmnaseri@programmer.net)
 * @since 1.0 (7/8/17, 8:11 PM)
 */
@FunctionalInterface
public interface ObjectWriter {

    void write(File file, Serializable object) throws IOException;

}

