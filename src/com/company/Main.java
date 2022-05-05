package com.company;

import Leitor.Leitor;
import grafo.Grafo;
import moa.Moa;
import path.Route;

import java.io.IOException;
import java.util.ArrayList;

public class Main {
    public final static String[] test = {
            "tests/pla33810.tsp",
            "tests/pla7397.tsp",
            "tests/d18512.tsp",
            "tests/fnl4461.tsp",
            "tests/pr1002.tsp",
            "tests/pla48.tsp",
            "tests/brd14051.tsp",
            "tests/d15112.tsp",
            "tests/pla85900.tsp"
    };

    public static void main(String[] args) throws IOException {
	    Leitor leitor= new Leitor();

	    ArrayList<ArrayList<String>> constructor = leitor.leitor(test[0]);
        Grafo g = new Grafo(constructor);
        Moa m = new Moa();
        Route r = m.nearestNeighbor(g);
        Route OPT2 = m.OPT2(r);
        System.out.println(OPT2.getWeight());
    }
}
