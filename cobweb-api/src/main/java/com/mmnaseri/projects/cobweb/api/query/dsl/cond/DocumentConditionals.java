package com.mmnaseri.projects.cobweb.api.query.dsl.cond;

import com.mmnaseri.projects.cobweb.api.query.dsl.DocumentTagsConditionalExpression;
import com.mmnaseri.projects.cobweb.api.query.dsl.Sources;
import com.mmnaseri.projects.cobweb.api.query.dsl.impl.DefaultDocumentTagsConditionalExpression;
import com.mmnaseri.projects.cobweb.domain.content.Document;

import java.io.Serializable;

/**
 * @author Mohammad Milad Naseri (mmnaseri@programmer.net)
 * @since 1.0 (7/14/17, 6:38 PM)
 */
public final class DocumentConditionals {

    private DocumentConditionals() {
        throw new UnsupportedOperationException();
    }

    public static <K extends Serializable & Comparable<K>, P extends Document<K>> DocumentTagsConditionalExpression<K, P> documentTags(Sources<K, P> sources) {
        return new DefaultDocumentTagsConditionalExpression<>("document.tags");
    }


}
