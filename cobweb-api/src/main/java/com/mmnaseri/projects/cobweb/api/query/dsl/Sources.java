package com.mmnaseri.projects.cobweb.api.query.dsl;

import com.mmnaseri.projects.cobweb.api.common.ParameterizedTypeReference;
import com.mmnaseri.projects.cobweb.domain.content.*;

import java.io.Serializable;

/**
 * @author Mohammad Milad Naseri (mmnaseri@programmer.net)
 * @since 1.0 (7/14/17, 2:43 PM)
 */
public final class Sources<K extends Serializable & Comparable<K>, P extends Persistent<K>> {

    private Sources() {
    }

    public static <K extends Serializable & Comparable<K>> Sources<K, Tag<K>> tags(Class<K> type) {
        return new Sources<>();
    }

    public static <K extends Serializable & Comparable<K>> Sources<K, Attachment<K>> attachments(Class<K> type) {
        return new Sources<>();
    }

    public static <K extends Serializable & Comparable<K>> Sources<K, Edge<K>> edges(Class<K> type) {
        return new Sources<>();
    }

    public static <K extends Serializable & Comparable<K>> Sources<K, Vertex<K>> vertices(Class<K> type) {
        return new Sources<>();
    }

    public static <K extends Serializable & Comparable<K>> Sources<K, Tag<K>> tags(ParameterizedTypeReference<K> type) {
        return new Sources<>();
    }

    public static <K extends Serializable & Comparable<K>> Sources<K, Attachment<K>> attachments(ParameterizedTypeReference<K> type) {
        return new Sources<>();
    }

    public static <K extends Serializable & Comparable<K>> Sources<K, Edge<K>> edges(ParameterizedTypeReference<K> type) {
        return new Sources<>();
    }

    public static <K extends Serializable & Comparable<K>> Sources<K, Vertex<K>> vertices(ParameterizedTypeReference<K> type) {
        return new Sources<>();
    }

}
