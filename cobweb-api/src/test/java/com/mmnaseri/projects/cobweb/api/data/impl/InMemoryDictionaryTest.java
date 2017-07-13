package com.mmnaseri.projects.cobweb.api.data.impl;

import com.mmnaseri.projects.cobweb.api.data.Dictionary;

import java.util.UUID;

/**
 * @author Mohammad Milad Naseri (mmnaseri@programmer.net)
 * @since 1.0 (7/12/17, 6:16 PM)
 */
public class InMemoryDictionaryTest extends BaseDictionaryTest {

    @Override
    protected Dictionary<UUID> setUpDictionary() throws Exception {
        return new InMemoryDictionary<>();
    }

}