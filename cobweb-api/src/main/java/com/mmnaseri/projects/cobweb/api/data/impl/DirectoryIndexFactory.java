package com.mmnaseri.projects.cobweb.api.data.impl;

import com.mmnaseri.projects.cobweb.api.data.Index;
import com.mmnaseri.projects.cobweb.api.data.IndexFactory;

import java.io.IOException;
import java.io.Serializable;

/**
 * @author Mohammad Milad Naseri (mmnaseri@programmer.net)
 * @since 1.0 (7/10/17, 8:08 PM)
 */
public class DirectoryIndexFactory implements IndexFactory<DirectoryIndexConfiguration> {

    private static final long serialVersionUID = -1361981396412346706L;

    @Override
    public <I extends Serializable & Comparable<I>, V extends Serializable> Index<I, V> getInstance(DirectoryIndexConfiguration configuration) {
        try {
            //noinspection unchecked
            return new DirectoryIndex<I, V>(configuration.getRoot(), configuration.getStringifier(), configuration.getObjectInputOutputManager());
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

}
