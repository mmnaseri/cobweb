package com.mmnaseri.projects.cobweb.domain.content;

import java.io.Serializable;

/**
 * @author Mohammad Milad Naseri (mmnaseri@programmer.net)
 * @since 1.0 (7/5/17, 10:17 PM)
 */
public class Tag<I extends Serializable> extends Persistent<I> {

    private static final long serialVersionUID = 2980693694492421432L;

    private String name;
    private String description;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public ObjectType getObjectType() {
        return ObjectType.TAG;
    }

}
