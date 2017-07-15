package com.mmnaseri.projects.cobweb.domain.id;

import java.io.Serializable;

/**
 * @author Mohammad Milad Naseri (mmnaseri@programmer.net)
 * @since 1.0 (7/5/17, 7:12 PM)
 */
public class Identifier<I extends Serializable & Comparable<I>> implements Serializable {

    private static final long serialVersionUID = 6647757186725575151L;

    private final I value;

    Identifier(I value) {
        this.value = value;
    }

    public I getValue() {
        return value;
    }

    @Override
    public String toString() {
        return String.valueOf(getValue());
    }

}
