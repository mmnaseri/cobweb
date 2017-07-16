package com.mmnaseri.projects.cobweb.api.graph.impl;

import com.mmnaseri.projects.cobweb.api.graph.IdentifierGenerator;

import java.io.Serializable;
import java.util.UUID;

/**
 * @author Mohammad Milad Naseri (mmnaseri@programmer.net)
 * @since 1.0 (7/16/17, 1:35 AM)
 */
public class UUIDIdentifierGenerator implements IdentifierGenerator<UUID>, Serializable {

    private static final long serialVersionUID = 6533734191331801000L;

    @Override
    public UUID next() {
        return UUID.randomUUID();
    }

}
