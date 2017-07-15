package com.mmnaseri.projects.cobweb.api.data.impl;

import com.mmnaseri.projects.cobweb.api.data.Index;
import com.mmnaseri.projects.cobweb.api.data.model.SerializableDocument;

import java.util.UUID;

/**
 * @author Mohammad Milad Naseri (mmnaseri@programmer.net)
 * @since 1.0 (7/15/17, 1:17 PM)
 */
public class InMemoryIndexTest extends BaseIndexTest {

    @Override
    protected Index<UUID, SerializableDocument> setUpIndex() throws Exception {
        return new InMemoryIndex<>();
    }

}