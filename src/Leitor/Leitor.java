package Leitor;


import path.Route;

import java.io.*;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Leitor {

    public static ArrayList<ArrayList<String>> leitor(String path) throws IOException {
        BufferedReader buffRead = new BufferedReader(new FileReader(path));

        ArrayList<ArrayList<String>> constructor = new ArrayList<>();
        String linha = "";
        int count = 0;
        while (true) {
            linha = buffRead.readLine();
            if ((linha != null) && (!linha.contains("EOF"))) {
                if (count > 5) {

                    String[] splitLine = linha.split(" ");
                    ArrayList<String> splitLineC = new ArrayList<>();
                    int i;
                    for (i = 0; i < splitLine.length; i++) {
                        if (!splitLine[i].equals("")) {
                            splitLineC.add(splitLine[i]);
                        }
                    }
                    constructor.add(splitLineC);



                }

            } else {
                break;
            }
            count++;
        }
        buffRead.close();
        int i;
        return constructor;
        
    }

    public static void escritor(ArrayList<Route> population,int Geracao) throws IOException {
        int numCidades = population.get(0).getVertices().size();
        DecimalFormat df = new DecimalFormat("0,00");
        String arqPath = new String("results/"+numCidades+".csv");
        File arquivo = new File(arqPath);
        if (!arquivo.exists()){
            arquivo.createNewFile();
            BufferedWriter buffWrite = new BufferedWriter(new FileWriter(arqPath,true));
            buffWrite.append("Geração");
            buffWrite.append(";");
            buffWrite.append("Fitness");
            buffWrite.append(";");
            buffWrite.append("\n");
            buffWrite.close();
        }
        BufferedWriter buffWrite = new BufferedWriter(new FileWriter(arqPath,true));
        String weight= Double.toString(population.get(0).getWeight()).replace('.',',');
        String geracao = Integer.toString(Geracao);

        buffWrite.append(geracao);
        buffWrite.append(";");
        buffWrite.append(weight);
        buffWrite.append(";");
        buffWrite.append("\n");
        buffWrite.close();
    }

}