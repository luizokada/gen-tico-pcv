package com.company;

import Leitor.*;
import grafo.Grafo;
import moa.Moa;
import path.Route;

import java.io.IOException;
import java.util.ArrayList;

public class Main {
    public final static String[] test = {

            "tests/pla7397.tsp",
            "tests/d18512.tsp",
            "tests/brd14051.tsp",
            "tests/d15112.tsp",
            "tests/pla33810.tsp",
            "tests/pla85900.tsp",
    };

    public static void main(String[] args) throws IOException {
	    Leitor leitor= new Leitor();
        for(int i = 0; i< test.length;i++){
            System.out.println("Executando: "+test[i]);
            ArrayList<ArrayList<String>> constructor = leitor.leitor(test[i]);
            Grafo g = new Grafo(constructor);
            Moa m = new Moa();
            m.AG(g);
        }
    }
}
