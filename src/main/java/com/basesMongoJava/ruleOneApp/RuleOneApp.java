package com.basesMongoJava.ruleOneApp;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class RuleOneApp {
    public static void main(String[] args) {

        String t1 = "pais";
        String t2 = "depto";

        Set<String> t1Attributes = new HashSet<>();
        t1Attributes.add("idp");
        t1Attributes.add("nomp");

        Set<String> t2Attributes = new HashSet<>();
        t2Attributes.add("codd");
        t2Attributes.add("ndep");
        t2Attributes.add("idp");
        t2Attributes.add("capital");

        ArrayList<String> s2Queries = new ArrayList<>();
        s2Queries.add("SELECT  nomp, codd, ndep,uno2 FROM PAIS NATURAL JOIN DEPTO");
//        s2Queries.add("SELECT codd, capital FROM DEPTO");
//        s2Queries.add("  select abd, nomp, nomp2, edf, gfrt,ajhdjadn from pais");

        ArrayList<String> s1Queries = new ArrayList<>();
        s1Queries.add("SELECT codd, capital FROM DEPTO");
//        s2Queries.add("  select abd, nomp, nomp2, edf, gfrt,ajhdjadn from pais");

        Rule1 rule1 = new Rule1(t1, t2, t1Attributes, t2Attributes, s1Queries,s2Queries);

        // Este es el resultado
        String newcollection = rule1.generateNewCollection();

    }
}
