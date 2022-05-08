package path;

import grafo.*;
import utils.*;

import java.util.ArrayList;
import java.util.Comparator;

public class Route implements Comparable<Route> {
    public ArrayList<Vertice> vertices = new ArrayList<Vertice>();
    public double weight = 0;


    public Route(){
        super();

    }
    public Route(Route r){
        this.vertices = (ArrayList<Vertice>) r.getVertices().clone();

    }
    public ArrayList<Vertice> getVertices() {
        return vertices;
    }

    public void setVertices(ArrayList<Vertice> vertices) {
        this.vertices = vertices;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight() {
        Calculator calc = new Calculator();

        if (this.weight != 0) {
            this.weight = 0;
        }
        int i = 0;
        for (i = 0; i < this.vertices.size(); i++) {
            if (i == this.vertices.size() - 1) {

                this.weight += calc.ED(this.vertices.get(i),
                        this.vertices.get(0));
            } else {
                this.weight += calc.ED(this.vertices.get(i),
                        this.vertices.get(i + 1));
            }
        }
    }

    public void addToRoute(Vertice v) {
        boolean add;
        add = this.vertices.add(v);
    }

    public void setWeight(double w) {
        this.weight = w;
    }


    @Override
    public int compareTo(Route o) {
        if (this.weight < o.getWeight()) {
            return -1;
        }
        if (this.weight > o.getWeight()) {
            return 1;
        }
        return 0;
    }
    public void print(){

        System.out.println("WEIGHT: "+ this.weight);
        System.out.print("[");
        for (int i =0 ;i<this.vertices.size();i++){
            System.out.print(this.vertices.get(i).getId()+" ,");
        }
        System.out.print("]\n");
    }

    public static Comparator<Route> StuRollno = new Comparator<Route>() {

        public int compare(Route s1, Route s2) {

            int rollno1 = (int) s1.getWeight();
            int rollno2 = (int) s2.getWeight();


            return rollno1 - rollno2;
        }

    };


}



