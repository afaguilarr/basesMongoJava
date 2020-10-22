package com.basesMongoJava.common;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class Rule1 {
    protected String t1;
    protected String t2;
    protected Set<String> t1Attributes;
    protected Set<String> t2Attributes;
    protected ArrayList<Set<String>> s1;
    protected ArrayList<Set<String>> s2;

    public Rule1(String t1, String t2, Set<String> t1Attributes, Set<String> t2Attributes, ArrayList<String> s1Queries, ArrayList<String> s2Queries) {
        this.t1 = t1;
        this.t2 = t2;
        this.t1Attributes = t1Attributes;
        this.t2Attributes = t2Attributes;
        this.s1 = Query.parseQueries(s1Queries, t2Attributes);

        Set<String> attributesUnion = new HashSet<>(t1Attributes);
        attributesUnion.addAll(t2Attributes);

        this.s2 = Query.parseQueries(s2Queries, attributesUnion);
    }

    protected Set<String> generateFirstDocument(Set<String> s2Attributes, ArrayList<Set<String>> s1) {
        Set<String> embeddedDocument = new HashSet<>(s2Attributes);
        ArrayList<Set<String>> s1Copy = new ArrayList<>();

        for(Set<String> set : s1) {
            s1Copy.add(new HashSet<>(set));
        }

        for (Set<String> queryAttributes : s1Copy) {
            Set<String> originalQueryAttributes = new HashSet<>(queryAttributes);
            queryAttributes.removeAll(s2Attributes);

            if (queryAttributes.isEmpty() || queryAttributes.equals(originalQueryAttributes)) {
                continue;
            }
            embeddedDocument.addAll(queryAttributes);
        }

        return embeddedDocument;
    }

    protected Set<String> generateSecondDocument(Set<String> s2Attributes, ArrayList<Set<String>> s1) {
        Set<String> embeddedDocument = new HashSet<>();
        ArrayList<Set<String>> s1Copy = new ArrayList<>();

        for(Set<String> set : s1) {
            s1Copy.add(new HashSet<>(set));
        }

        for (Set<String> queryAttributes : s1Copy) {
            Set<String> intersectionAttributes = new HashSet<>(queryAttributes);
            intersectionAttributes.retainAll(s2Attributes);

            if (intersectionAttributes.isEmpty()) {
                embeddedDocument.addAll(queryAttributes);
            }
        }

        return embeddedDocument;
    }

    public String generateNewCollection() {
        String newCollection = "Q'={";

        newCollection += Collection.setToString(t1Attributes);

        Set<String> s2Attributes = Collection.getUnion(s2);
        s2Attributes.retainAll(t2Attributes);
        Set<String> firstEmbeddedDocument = generateFirstDocument(s2Attributes, s1);

        if (!firstEmbeddedDocument.isEmpty()) {
            newCollection += String.format(", %s_of_%s:{%s}", t2, t1, Collection.setToString(firstEmbeddedDocument));
        }

        Set<String> secondEmbeddedDocument = generateSecondDocument(s2Attributes, s1);
        if (!secondEmbeddedDocument.isEmpty()) {
            newCollection += String.format(", %s_T''2_%s:{%s}", t2, t1, Collection.setToString(secondEmbeddedDocument));
        }

        return newCollection + "}";
    }

}
