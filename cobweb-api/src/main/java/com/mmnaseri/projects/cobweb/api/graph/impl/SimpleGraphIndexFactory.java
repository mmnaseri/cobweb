package com.mmnaseri.projects.cobweb.api.graph.impl;

import com.mmnaseri.projects.cobweb.api.common.SerializableMap;
import com.mmnaseri.projects.cobweb.api.common.SerializableSet;
import com.mmnaseri.projects.cobweb.api.data.Index;
import com.mmnaseri.projects.cobweb.domain.content.Attachment;
import com.mmnaseri.projects.cobweb.domain.content.DocumentProperties;
import com.mmnaseri.projects.cobweb.domain.content.Tag;

import java.io.Serializable;

/**
 * @author Mohammad Milad Naseri (mmnaseri@programmer.net)
 * @since 1.0 (7/13/17, 3:28 PM)
 */
class SimpleGraphIndexFactory<K extends Serializable & Comparable<K>> {

    Index<K, DocumentProperties> vertices() {
        return null;
    }

    Index<K, DocumentProperties> edges() {
        return null;
    }

    Index<K, Tag<K>> tags() {
        return null;
    }

    Index<K, Attachment<K>> attachments() {
        return null;
    }

    Index<K, SerializableSet<K>> incomingEdges() {
        return null;
    }

    Index<K, SerializableSet<K>> outgoingEdges() {
        return null;
    }
    Index<K, SerializableMap<String, K>> documentAttachments() {
        return null;
    }

    Index<K, SerializableSet<K>> anchors() {
        return null;
    }

    Index<K, SerializableSet<K>> documentTags() {
        return null;
    }

    Index<K, SerializableSet<K>> tagDocuments() {
        return null;
    }

}
