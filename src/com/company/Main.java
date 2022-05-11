package com.company;

import Leitor.*;
import grafo.Grafo;
import moa.Moa;
import path.Route;

import java.io.IOException;
import java.util.ArrayList;

public class Main {
    public final static String[] test = {
            "tests/pla48.tsp",
            "tests/pr1002.tsp",
            "tests/fnl4461.tsp",
            "tests/pla7397.tsp",
            "tests/brd14051.tsp",
            "tests/d15112.tsp",
            "tests/d18512.tsp",
            "tests/pla33810.tsp",
            "tests/pla85900.tsp",
    };

    public static void main(String[] args) throws IOException {
	    Leitor leitor= new Leitor();
	    if (args[0].equals("test")){

            for(int i = 0; i< test.length;i++){
                long initTime = System.currentTimeMillis();
                System.out.println("EXECUTANDO COM O CX");
                System.out.println("Executando: "+test[i]);
                ArrayList<ArrayList<String>> constructor = leitor.leitor(test[i]);
                Grafo g = new Grafo(constructor);
                Moa m = new Moa();
                m.AG(g);
                long spentTime = System.currentTimeMillis();
                long totalTime = spentTime-initTime;

                System.out.println("Tempo: " + (totalTime) + "ms");


                long initTime1 = System.currentTimeMillis();
                System.out.println("EXECUTANDO COM O PMX");
                System.out.println("Executando: "+test[i]);
                ArrayList<ArrayList<String>> constructor1 = leitor.leitor(test[i]);
                Grafo g1 = new Grafo(constructor1);
                Moa m1 = new Moa();
                m1.AG1(g1);
                long spentTime1 = System.currentTimeMillis();
                long totalTime1 = spentTime1-initTime1;

                System.out.println("Tempo: " + (totalTime1) + "ms");
            }
        }
	    else {
            long initTime = System.currentTimeMillis();
            System.out.println("EXECUTANDO COM O CX");
            System.out.println("Executando: "+args[0]);
            ArrayList<ArrayList<String>> constructor = leitor.leitor(args[0]);
            Grafo g = new Grafo(constructor);
            Moa m = new Moa();
            m.AG(g);
            long spentTime = System.currentTimeMillis();
            long totalTime = spentTime-initTime;

            System.out.println("Tempo: " + (totalTime) + "ms");


            long initTime1 = System.currentTimeMillis();
            System.out.println("EXECUTANDO COM O PMX");
            System.out.println("Executando: "+args[0]);
            ArrayList<ArrayList<String>> constructor1 = leitor.leitor(args[0]);
            Grafo g1 = new Grafo(constructor1);
            Moa m1 = new Moa();
            m1.AG1(g1);
            long spentTime1 = System.currentTimeMillis();
            long totalTime1 = spentTime1-initTime1;

            System.out.println("Tempo: " + (totalTime1) + "ms");
        }

        }




}
