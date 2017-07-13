package com.mmnaseri.projects.cobweb.api.data.impl;

import com.mmnaseri.projects.cobweb.api.data.Stringifier;

import java.util.UUID;

/**
 * @author Mohammad Milad Naseri (mmnaseri@programmer.net)
 * @since 1.0 (7/12/17, 5:40 PM)
 */
public class UUIDStringifier implements Stringifier<UUID> {

    @Override
    public UUID fromString(String string) {
        return UUID.fromString(string);
    }

    @Override
    public String toString(UUID value) {
        return value.toString();
    }

}
