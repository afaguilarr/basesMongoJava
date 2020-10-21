package com.basesMongoJava.ruleOneApp;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class Collection {

    public static Set<String> getUnion(ArrayList<Set<String>> attributesSets) {
        Set<String> attributesUnion = new HashSet<>();

        for (Set<String> set : attributesSets) {
            attributesUnion.addAll(set);
        }

        return attributesUnion;
    }

    public static Set<String> getFirstDocumentUnion(ArrayList<Set<String>> attributesSets) {
        Set<String> attributesUnion = new HashSet<>();

        for (Set<String> set : attributesSets) {
            attributesUnion.addAll(set);
        }

        return attributesUnion;
    }


    public static Set<String> getIntersection(ArrayList<Set<String>> attributesSets) {
        Set<String> attributesIntersection = new HashSet<>();

        for (Set<String> set : attributesSets) {
            attributesIntersection.retainAll(set);
        }

        return attributesIntersection;
    }

    public static String setToString(Set<String> set) {
        StringBuilder result = new StringBuilder();

        if (set.isEmpty()) {
            return result.toString();
        }

        for (String attribute : set) {
            result.append(attribute).append(", ");
        }
        return result.substring(0, result.length() - 2);
    }

    public static String setToStringWithBrackets(Set<String> set) {
        return String.format("{%s}", setToString(set));
    }
}