package path;

import grafo.Vertice;
import utils.Calculator;

import java.util.ArrayList;

public class Route {
    ArrayList<Vertice> vertices = new ArrayList<>();
    double weight=0;

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
        if (this.weight!=0){
            this.weight = 0;
        }
        int i = 0;
        for(i=0;i<this.vertices.size();i++){
            if (i==this.vertices.size()-1){
                this.weight=this.weight+calc.ED(this.vertices.get(i),this.vertices.get(0));
            }
            else{
                this.weight=this.weight+calc.ED(this.vertices.get(i),this.vertices.get(i+1));
            }
        }
    }

    public void addToRoute(Vertice v){
        this.vertices.add(v);
    }


}
