package com.mmnaseri.projects.cobweb.domain.content;

import com.mmnaseri.projects.cobweb.domain.id.Identifier;

import java.io.Serializable;

/**
 * @author Mohammad Milad Naseri (mmnaseri@programmer.net)
 * @since 1.0 (7/5/17, 10:12 PM)
 */
public abstract class Persistent<I extends Serializable> implements Serializable {

    private static final long serialVersionUID = -6002064779952352711L;

    private Identifier<I> id;

    public Identifier<I> getId() {
        return id;
    }

    public void setId(Identifier<I> id) {
        this.id = id;
    }

    public abstract ObjectType getObjectType();

}
