package utils;

import grafo.Vertice;

public class Calculator {

    public double ED (Vertice a, Vertice B){
        int deltaX = a.getX() - B.getX();
        int deltaY = a.getY() - B.getY();
        return Math.sqrt((Math.pow(deltaX,2) + Math.pow(deltaY,2)));
    }
}
