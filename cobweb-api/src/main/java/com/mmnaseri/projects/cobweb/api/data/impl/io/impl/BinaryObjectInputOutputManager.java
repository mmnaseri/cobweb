package com.mmnaseri.projects.cobweb.api.data.impl.io.impl;

import com.mmnaseri.projects.cobweb.api.data.impl.io.ObjectInputOutputManager;
import com.mmnaseri.projects.cobweb.api.data.impl.io.ObjectReader;
import com.mmnaseri.projects.cobweb.api.data.impl.io.ObjectWriter;

/**
 * @author Mohammad Milad Naseri (mmnaseri@programmer.net)
 * @since 1.0 (7/8/17, 8:12 PM)
 */
public class BinaryObjectInputOutputManager implements ObjectInputOutputManager {

    @Override
    public ObjectReader getReader() {
        return new BinaryObjectReader();
    }

    @Override
    public ObjectWriter getWriter() {
        return new BinaryObjectWriter();
    }

}
