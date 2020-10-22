package com.basesMongoJava.common;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Query {

    public static Set<String> parseQuery(String query) {
        // regex for query
        query = query.toLowerCase();
        query = query.replaceFirst("\\s*(select)\\s*", "");
        query = query.replaceAll("\\s*(from).*", "");

        return new HashSet<>(Arrays.asList(query.split(",\\s*")));
    }

    public static ArrayList<Set<String>> parseQueries(ArrayList<String> queries, Set<String> tAttributes) {
        ArrayList<Set<String>> queriesAttributes = new ArrayList<>();

        for (String query : queries) {
            Set<String> queryAttributes = parseQuery(query);
            if (queryAttributes.iterator().hasNext() && queryAttributes.iterator().next().equals("*")) {
                queriesAttributes.add(tAttributes);
                continue;
            }
            queriesAttributes.add(parseQuery(query));
        }

        return queriesAttributes;
    }


}
