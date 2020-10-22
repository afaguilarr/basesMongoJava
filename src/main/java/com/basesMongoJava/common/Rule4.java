package com.basesMongoJava.common;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class Rule4 extends Rule1{
    private String t3;
    private Set<String> t3Attributes;
    private ArrayList<Set<String>> s3;

    public Rule4(String t1, String t2, String t3, Set<String> t1Attributes,
                 Set<String> t2Attributes, Set<String> t3Attributes,
                 ArrayList<String> s1Queries, ArrayList<String> s2Queries,
                 ArrayList<String> s3Queries) {
        super(t1, t2, t1Attributes, t2Attributes, s1Queries, s2Queries);
        this.s1 = Query.parseQueries(s1Queries, t1Attributes);
        this.t3 = t3;
        this.t3Attributes = t3Attributes;

        Set<String> attributesUnion = new HashSet<>(t1Attributes);
        attributesUnion.addAll(super.t2Attributes);
        attributesUnion.addAll(this.t3Attributes);

        this.s3 = Query.parseQueries(s3Queries, attributesUnion);
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
        }

        Set<String> secondEmbeddedDocument = generateSecondDocument(t2s3Attributes, s1);
        if (!secondEmbeddedDocument.isEmpty()) {
            newCollection += String.format(", %s_T''2_%s:{%s}", t2, t1, Collection.setToString(secondEmbeddedDocument));
        }

        Set<String> t3s3Attributes = Collection.getUnion(s3);
        t3s3Attributes.retainAll(t3Attributes);
        firstEmbeddedDocument = generateFirstDocument(t3s3Attributes, s1);

        if (!firstEmbeddedDocument.isEmpty()) {
            newCollection += String.format(", %s_of_%s:{%s}", t3, t1, Collection.setToString(firstEmbeddedDocument));
        }

        secondEmbeddedDocument = generateSecondDocument(t3s3Attributes, s1);
        if (!secondEmbeddedDocument.isEmpty()) {
            newCollection += String.format(", %s_T''3_%s:{%s}", t3, t1, Collection.setToString(secondEmbeddedDocument));
        }
        return newCollection + "}";
    }
}
