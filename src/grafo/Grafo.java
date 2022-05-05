package grafo;

import java.util.ArrayList;

public class Grafo {

    ArrayList<Vertice> vertices;


    public Grafo(ArrayList<ArrayList<String>> c){
        this.vertices=new ArrayList<>();
        int i;
        for (i=0;i<c.size();i++){
            int id = Integer.parseInt(c.get(i).get(0))-1;
            int x = Integer.parseInt(c.get(i).get(1));
            int y = Integer.parseInt(c.get(i).get(2));
            Vertice v = new Vertice(id,x,y);
            this.vertices.add(v);
        }

    }
    public ArrayList<Vertice> getVertices() {
        return vertices;
    }

    public void setVertices(ArrayList<Vertice> vertices) {
        this.vertices = vertices;
    }

}
