package com.basesMongoJava.common;

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
}
