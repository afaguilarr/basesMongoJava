package com.basesMongoJava.common;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class Rule5 extends Rule4 {

    public Rule5(String t1, String t2, String t3, Set<String> t1Attributes,
                 Set<String> t2Attributes, Set<String> t3Attributes,
                 ArrayList<String> s1Queries, ArrayList<String> s2Queries,
                 ArrayList<String> s3Queries) {
        super(t1, t2, t3, t1Attributes, t2Attributes, t3Attributes, s1Queries, s2Queries, s3Queries);
    }

    @Override
    public String generateNewCollection() {
        String newCollection = "Q'={";

        newCollection += Collection.setToString(t1Attributes);

        Set<String> t2s3Attributes = Collection.getUnion(s3);
        t2s3Attributes.retainAll(t2Attributes);
        Set<String> firstEmbeddedDocument = generateFirstDocument(t2s3Attributes, s1);

        if (!firstEmbeddedDocument.isEmpty()) {
            newCollection += String.format(", %s_of_%s:{%s}", t2, t1, Collection.setToString(firstEmbeddedDocument));
            newCollection = newCollection.substring(0, newCollection.length() - 1);
        }

        Set<String> t3s3Attributes = Collection.getUnion(s3);
        t3s3Attributes.retainAll(t3Attributes);
        Set<String> firstEmbeddedDocumentAlpha = generateFirstDocument(t3s3Attributes, s1);

        if (!firstEmbeddedDocumentAlpha.isEmpty()) {
            newCollection += String.format(", %s_of_%s:{%s}", t3, t1, Collection.setToString(firstEmbeddedDocumentAlpha));
        }

        if (!firstEmbeddedDocument.isEmpty()) newCollection += "}";

        Set<String> secondEmbeddedDocument = generateSecondDocument(t2s3Attributes, s1);
        if (!secondEmbeddedDocument.isEmpty()) {
            newCollection += String.format(", %s_T''2_%s:{%s}", t2, t1, Collection.setToString(secondEmbeddedDocument));
        }

        secondEmbeddedDocument = generateSecondDocument(t3s3Attributes, s1);
        if (!secondEmbeddedDocument.isEmpty()) {
            newCollection += String.format(", %s_T''3_%s:{%s}", t3, t1, Collection.setToString(secondEmbeddedDocument));
        }
        return newCollection + "}";
    }
}
