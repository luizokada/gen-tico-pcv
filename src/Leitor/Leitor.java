package Leitor;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
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

    public static void escritor(String path) throws IOException {
        BufferedWriter buffWrite = new BufferedWriter(new FileWriter(path));
        String linha = "";
        Scanner in = new Scanner(System.in);
        System.out.println("Escreva algo: ");
        linha = in.nextLine();
        buffWrite.append(linha + "\n");
        buffWrite.close();
    }

}