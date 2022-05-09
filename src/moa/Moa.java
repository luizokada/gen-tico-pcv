package moa;

import Leitor.Leitor;
import grafo.*;

import path.*;
import utils.Calculator;

import java.io.IOException;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;


public class Moa {
    final static int POPULATION_SIZE = 100;
    final static double MUTATION_RATE = 0.05;
    final static int GERACAO = 100;
    ArrayList<Route> population = new ArrayList<Route>();



    public boolean isAllInRoute(ArrayList<Boolean> inRoute){
        int i;
        for (i=0;i<inRoute.size();i++){
            if(!inRoute.get(i).booleanValue()){
                return false;
            }
        }
        return true;
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

    public Route nearestNeighbor(Grafo g){
        Random rand = new Random();

        int id = rand.nextInt(g.getVertices().size());
        Route r = new Route();
        r.addToRoute(g.getVertices().get(id));
        ArrayList<Boolean> inRoute = new ArrayList<>(Arrays.asList(new Boolean[g.getVertices().size()]));
        Collections.fill(inRoute,Boolean.FALSE);
        inRoute.set(id,Boolean.TRUE);
        while (!isAllInRoute(inRoute)){
            int size = r.getVertices().size();
            Vertice v = getNearest(g,r.getVertices().get(size-1),inRoute);
            inRoute.set(v.getId(),Boolean.TRUE);
            r.addToRoute(v);
        }
        r.setWeight();

        return r;

    }


    public Route OPT2(Route route){
        int i,j;
        boolean isBetter = true;
        double oldWeight = route.getWeight();
        Calculator calc = new Calculator();
        while(isBetter) {
            double newWeight = oldWeight;
            for (i = 1; i < route.getVertices().size() - 2; i++) {
                for (j = i + 1; j < route.getVertices().size()-1; j++) {
                    double entryCost;
                    double exitCost;

                    entryCost = calc.ED(route.getVertices().get(i), route.getVertices().get(j+1)) +
                            calc.ED(route.getVertices().get(i-1), route.getVertices().get(j));
                    exitCost = calc.ED(route.getVertices().get(i), route.getVertices().get(i-1)) +
                            calc.ED(route.getVertices().get(j+1), route.getVertices().get(j));

                    if (entryCost < exitCost) {
                        Collections.reverse(route.getVertices().subList(i, j + 1));
                        newWeight-=(exitCost - entryCost);
                    }
                }
            }
            if (oldWeight == newWeight) {
                isBetter=false;
            }
        }

        route.setWeight();
        return route;
    }

    public void initPopulation (Grafo g){

        int i =0;
        for (i=0;i<POPULATION_SIZE;i++){
            this.population.add(nearestNeighbor(g));
        }
    }


    public PriorityQueue<Route> selecao(){
        PriorityQueue<Route> filhos = new PriorityQueue<>();
        int k =3;
        for (int i =0; i<(this.POPULATION_SIZE/2);i++){
            ArrayList<Route> selected = tournament(k);
            CX(selected,filhos);
        }
        return filhos;

    }

    public ArrayList<Route> tournament(int k){
        Random rand = new Random();
        ArrayList<Route> competitors = new ArrayList<>();
        ArrayList<Route> selected = new ArrayList<>();
        while(competitors.size()<3){
            int comp = rand.nextInt(POPULATION_SIZE-1);
            competitors.add(this.population.get(comp));
        }
        double select = competitors.get(0).getWeight();
        if (select<competitors.get(1).getWeight()){
            selected.add(competitors.get(0));
            select = competitors.get(1).getWeight();
            if (select<competitors.get(2).getWeight()){
                selected.add(competitors.get(1));
            }
            else{
                selected.add(competitors.get(2));
            }
        }
        else{
            selected.add(competitors.get(1));
            if (select<competitors.get(2).getWeight()){
                selected.add(competitors.get(0));
            }
            else{
                selected.add(competitors.get(2));
            }
        }

        return selected;
    }

    public void Mutation(Route filho){
        Random rand = new Random();
        int willBeMutation = rand.nextInt(100);
        if (willBeMutation<=MUTATION_RATE*100){
            int i = rand.nextInt(filho.getVertices().size());
            int j = rand.nextInt(filho.getVertices().size());
            Vertice aux = filho.getVertices().get(i);
            filho.getVertices().set(i,filho.getVertices().get(j));
            filho.getVertices().set(j,aux);
        }
    }
    public void CX(ArrayList<Route> selected, PriorityQueue<Route> pop){
        ArrayList<Route> filhos = new ArrayList<>();

        int pai = 1;
        for (int i = 0; i<2;i++){
            Route filhoi = new Route(selected.get(pai));

            int index =0;
                do{
                    filhoi.getVertices().set(index,selected.get(i).getVertices().get(index));
                    index = selected.get(pai).getVertices().indexOf(filhoi.getVertices().get(index));
                }while(index!=0);
            Mutation(filhoi);
            filhoi.setWeight();
            Route filhoBusca = OPT2(filhoi);
            filhos.add(filhoBusca);
            pai=0;
        }
        pop.add(filhos.get(0));
        pop.add(filhos.get(1));
    }

    public void PMX(Route firstParent, Route secondParent, PriorityQueue<Route> pop) {
        Route firstChild = new Route(firstParent);
        Route secondChild = new Route(secondParent);

        int firstPoint = (int) selectCuttingPoint(0,firstParent.getVertices().size()-2);
        int secondPoint = (int) selectCuttingPoint(firstPoint,firstParent.getVertices().size()-1);


        if (firstPoint==secondPoint){
            int aux = firstPoint;
            firstPoint = secondPoint;
            secondPoint = aux;
        }

        List<Vertice> firstGene = secondParent.getVertices().subList(firstPoint, secondPoint);
        int index = 0;
        for (int i = firstPoint; i < secondPoint; i++) {
            firstChild.getVertices().set(i, firstGene.get(index));
            index++;
        }

        List<Vertice> secondGene = firstParent.getVertices().subList(firstPoint, secondPoint);
        index = 0;
        for (int i = firstPoint; i < secondPoint; i++) {
            secondChild.getVertices().set(i, secondGene.get(index));
            index++;
        }

        // removing first parent chromosomes duplicated in first child
        Route firstList = selectCitiesNotCopied(firstChild, secondParent);

        for (Vertice c : firstList.getVertices()) {
            Vertice aux = c;
            while (firstPoint <= firstParent.getVertices().indexOf(c) && secondPoint >= firstParent.getVertices().indexOf(c)) {

                c = firstChild.getVertices().get(firstParent.getVertices().indexOf(c));
            }
            firstChild.getVertices().set(firstParent.getVertices().indexOf(c), aux);
        }

        // removing second parent chromosomes duplicated in second child
        Route secondList = selectCitiesNotCopied(secondChild, firstParent);

        for (Vertice c : secondList.getVertices()) {
            Vertice aux = c;
            while (firstPoint <= secondParent.getVertices().indexOf(c) && secondPoint >= secondParent.getVertices().indexOf(c)) {
                c = secondChild.getVertices().get(secondParent.getVertices().indexOf(c));
            }
            secondChild.getVertices().set(secondParent.getVertices().indexOf(c), aux);
        }

        // local search
        Route firstChildSearch = OPT2(firstChild);
        Route secondChildSearch = OPT2(secondChild);



        pop.add(firstChild);
        pop.add(secondChild);

    }

    public Route selectCitiesNotCopied(Route child, Route gene) {
        // dif entre o filho e o gene
        Route copyChild = new Route(child);
        copyChild.getVertices().removeAll(gene.getVertices());
        return copyChild;
    }

    public int selectCuttingPoint(int min , int max){

        return ThreadLocalRandom.current().nextInt(min, max + 1);
    }

    public void Evaluation(PriorityQueue<Route> filhos){
        boolean menor = true;
        int tamanho = POPULATION_SIZE -1;

        ArrayList<Route> filhosToBeAdded = new ArrayList<>();
        int Biggestweight = (int) population.get(tamanho).getWeight();
        while (menor){
            try {
                Route filho = filhos.remove();
                if(filho.getWeight()<Biggestweight){
                    filhosToBeAdded.add(filho);
                }
                else{
                    menor=false;
                }
            }finally {
                menor  =false;
            }

        }
        for (int i =0 ;i<filhosToBeAdded.size();i++){
            Route filho = filhosToBeAdded.get(i);
            for (int j = 0;j<population.size();j++){
                if (filho.getWeight()<=population.get(j).getWeight()){
                    population.set(j,filho);
                    break;
                }
            }

        }

    }


    public void AG(Grafo g) throws IOException {
        Leitor escritor = new Leitor();

        int geracao = 0;
        this.initPopulation(g);
        Collections.sort(population,Route.StuRollno);
        for(int j = 0 ; j<POPULATION_SIZE;j++){
            System.out.print(population.get(j).getWeight() + " , ");
        }
        System.out.print("\n ");
        escritor.escritor(this.population,geracao);
        while (geracao<GERACAO){
            PriorityQueue<Route> filhos = this.selecao();
            System.out.print("SELECAO PRONTA \n");
            this.Evaluation(filhos);
            System.out.println("INTERACAO: "+ geracao+"\n");
            System.out.print("BESTSOLUTION: "+population.get(0).getWeight()+"\n");
            escritor.escritor(this.population,geracao);
            geracao++;
        }
        Route bestSolution = OPT2(population.get(0));
        bestSolution.print();
    }

}
