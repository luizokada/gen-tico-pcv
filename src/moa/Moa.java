package moa;

import grafo.Vertice;
import grafo.Grafo;
import path.Route;
import utils.Calculator;

import java.util.*;


public class Moa {
    final static int POPULATION_SIZE = 1000;
    final static double MUTATION_RATE = 0.05;
    final static int INTERATIONS = 50;
    ArrayList<Route> population = new ArrayList<>();

    public void initPopulation (Grafo g){

        int i =0;
        for (i=0;i<POPULATION_SIZE;i++){
            this.population.add(nearestNeighbor(g));
        }
    }
    public Route OX1(Route pai1, Route pai2){
        return pai1;
    }

    public Route CX1(Route pai1, Route pai2){
        return pai1;
    }

    public void selecao(){

    }

    public void tournamentr(){

    }

    public void MO(){

    }

    public void AG(){

    }
    public boolean isAllInRoute(ArrayList<Boolean> inRoute){
        int i;
        for (i=0;i<inRoute.size();i++){
            if(!inRoute.get(i).booleanValue()){
                return false;
            }
        }
        return true;
    }

    public Route nearestNeighbor(Grafo g){
        Random rand = new Random();

        int id = rand.nextInt(g.getVertices().size());
        Route r = new Route();
        r.addToRoute(g.getVertices().get(id));
        ArrayList<Boolean> inRoute = new ArrayList<>(Arrays.asList(new Boolean[g.getVertices().size()]));
        Collections.fill(inRoute,Boolean.FALSE);
        while (!isAllInRoute(inRoute)){
            int size = r.getVertices().size();
            Vertice v = getNearest(g,r.getVertices().get(size-1),inRoute);
            inRoute.set(v.getId(),Boolean.TRUE);
            r.addToRoute(v);
        }
        r.setWeight();
        System.out.println(r.getWeight());

        return r;

    }

    public Vertice getNearest(Grafo g, Vertice v, ArrayList<Boolean> inRoute){
        double min = Double.POSITIVE_INFINITY;
        int vertexId =-1;
        Calculator calc = new Calculator();
        int i = 0;
        for(i=0;i<g.getVertices().size();i++){
            if (!inRoute.get(i).booleanValue()){
                double dist = calc.ED(v,g.getVertices().get(i));
                if (dist<min) {
                    min = dist;
                    vertexId = g.getVertices().get(i).getId();
                }
            }
        }
        return g.getVertices().get(vertexId);
    }

    public Route OPT2(Route route){
        int i,j,indexI=0,indexJ = 0;
        boolean isBetter = false;
        Calculator calc = new Calculator();
        for (i=1;i<route.getVertices().size()-1;i++){
            for (j=i+2;j<route.getVertices().size();j++){
                int aftJ;
                int antI = i-1;
                double entryCost,exitCost,newWeight;
                if (j==route.getVertices().size()-1){
                    aftJ=0;
                }
                else{
                   aftJ=j+1;
                }
                entryCost = calc.ED(route.getVertices().get(i),route.getVertices().get(aftJ)) +
                        calc.ED(route.getVertices().get(antI),route.getVertices().get(j));
                exitCost=calc.ED(route.getVertices().get(i),route.getVertices().get(antI)) +
                        calc.ED(route.getVertices().get(aftJ),route.getVertices().get(j));
                newWeight = route.getWeight() + entryCost - exitCost;
                if (newWeight< route.getWeight()){
                    indexI=i;
                    indexJ=j;
                    isBetter=true;
                    break;

                }

            }
            if(isBetter){
                break;
            }
        }

        if(isBetter){

            Collections.reverse(route.getVertices().subList(indexI, indexJ+1));
            route.setWeight();

            return OPT2(route);
        }
        return route;
    }
}
