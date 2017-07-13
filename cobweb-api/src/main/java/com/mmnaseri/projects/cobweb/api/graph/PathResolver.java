package com.mmnaseri.projects.cobweb.api.graph;

import java.nio.file.Path;

/**
 * @author Mohammad Milad Naseri (mmnaseri@programmer.net)
 * @since 1.0 (7/12/17, 7:47 PM)
 */
public interface PathResolver {

    Path resolve(Object object);

    Path edges();
}
