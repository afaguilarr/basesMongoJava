package com.basesMongoJava.ruleOneApp;

import java.util.ArrayList;
import java.util.Set;

public class Rule1 {
    private String t1;
    private String t2;
    private Set<String> t1Attributes;
    private Set<String> t2Attributes;
    private ArrayList<Set<String>> s1;
    private ArrayList<Set<String>> s2;

    Rule1(String t1, String t2, Set<String> t1Attributes, Set<String> t2Attributes, ArrayList<String> s1Queries, ArrayList<String> s2Queries) {
        this.t1 = t1;
        this.t2 = t2;
        this.t1Attributes = t1Attributes;
        this.t2Attributes = t2Attributes;
        this.s1 = Query.parseQueries(s1Queries);
        this.s2 = Query.parseQueries(s2Queries);
    }

    private Set<String> generateFirstDocument(Set<String> s2Attributes, ArrayList<Set<String>> s1) {
        // generación de doc embebido 1
        return null;
    }

    public String generateNewCollection() {
        String newCollection = "Q'={";

        newCollection += Collection.setToString(t1Attributes);

        // considerar depto_of_pais vacio?

        Set <String> s2Attributes = Collection.getUnion(s2);
        Set <String> firstEmbeddedDocument = generateFirstDocument(s2Attributes, s1);

        newCollection += ", " + String.format(", %s_of_%s:{%S}", t2, t1,Collection.setToString(firstEmbeddedDocument));



        return newCollection;
    }

}
