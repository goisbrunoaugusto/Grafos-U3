package com.ufrn;

import com.ufrn.algorithm.*;
import com.ufrn.miscs.Miscs;
import com.ufrn.miscs.Pair;
import com.ufrn.model.Grafo;
import com.ufrn.util.LeitorExcel;
import org.apache.commons.math3.exception.NullArgumentException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class MemeticoApp {
    static Pair<List<Integer>,Double> menorValor = null;
    static double mediaValores = 0;
    static long tempoExecucao = 0;

    public static void main( String[] args ) {
        System.out.println("=== Algoritmo Memético ===");
        try {
            String planilha = "/planilha.xlsx";
            String planilhaProblema9e10 = "/planilhaProblema9e10.xlsx";

            for (int i = 0; i < 20; i++) {
                rodarProblema(1, planilha, 48, 0);
            }
            printResultados("Problema 1", "km");

            for (int i = 0; i < 20; i++) {
                rodarProblema(2, planilha, 48, 1);
            }
            printResultados("Problema 2", "min");

            for (int i = 0; i < 20; i++) {
                rodarProblema(3, planilha, 36, 0);
            }
            printResultados("Problema 3", "km");

            for (int i = 0; i < 20; i++) {
                rodarProblema(4, planilha, 36, 1);
            }
            printResultados("Problema 4", "min");

            for (int i = 0; i < 20; i++) {
                rodarProblema(5, planilha, 24, 0);
            }
            printResultados("Problema 5", "km");

            for (int i = 0; i < 20; i++) {
                rodarProblema(6, planilha, 24, 1);
            }
            printResultados("Problema 6", "min");

            for (int i = 0; i < 20; i++) {
                rodarProblema(7, planilha, 12, 0);
            }
            printResultados("Problema 7", "km");

            for (int i = 0; i < 20; i++) {
                rodarProblema(8, planilha, 12, 1);
            }
            printResultados("Problema 8", "min");

            for (int i = 0; i < 20; i++) {
                rodarProblema(9, planilhaProblema9e10, 7, 0);
            }
            printResultados("Problema 9", "km");

            for (int i = 0; i < 20; i++) {
                rodarProblema(10, planilhaProblema9e10, 7, 1);
            }
            printResultados("Problema 10", "min");

            for (int i = 0; i < 20; i++) {
                rodarProblema(11, planilha, 6, 0);
            }
            printResultados("Problema 11", "km");

            for (int i = 0; i < 20; i++) {
                rodarProblema(12, planilha, 6, 1);
            }
            printResultados("Problema 12", "min");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void printResultados(String problema, String unidade) {
        try{
            String text = "------------------------------------------------------------\n" +
                    problema + "\n" +
                    "Menor valor: " + String.format("%.2f", menorValor.value()) + unidade + "\n" +
                    "Média: " + String.format("%.2f", mediaValores) + unidade + "\n" +
                    "Tempo de execução: " + tempoExecucao + "ms\n";
            System.out.println(text);

            Files.write(Paths.get("src/main/java/com/ufrn/results/MemeticoResults.txt"), text.getBytes(), StandardOpenOption.APPEND);
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public static void rodarProblema(int id, String planilha, int numVertice, int aba) {
        try {
            menorValor = null;
            mediaValores = 0;
            tempoExecucao = 0;

            long start = System.currentTimeMillis();
            LeitorExcel leitor = new LeitorExcel();
            Grafo grafo = leitor.popularGrafo(planilha, numVertice, aba);

            List<Pair<List<Integer>, Double>> populationList = Fitness.setPopulationWithClosestNeighbourAndSwap(grafo.getNumVertices(), grafo);
            populationList.sort(Comparator.comparingDouble(Pair<List<Integer>, Double>::value).reversed());

            List<Pair<List<Integer>, Double>> parentList = Selection.elitismSelection(populationList, .8);
            if(parentList == null){
                throw new NullArgumentException();
            }

            List<Pair<List<Integer>, Double>> offspringList = Crossover.onePoint(grafo, parentList);

            List<Pair<List<Integer>, Double>> mutatedOffspringList = Mutation.mutate(grafo, offspringList, .5);

            BuscaLocal bl = new BuscaLocal();
            List<Pair<List<Integer>, Double>> memeticOffspringList = new ArrayList<>();

            for (Pair<List<Integer>, Double> individuo : mutatedOffspringList) {
                List<Integer> rotaAtual = individuo.key();

                rotaAtual = bl.executarSwap(grafo, rotaAtual);

                rotaAtual = bl.executarShift(grafo, rotaAtual);

                rotaAtual = bl.executarInversao(grafo, rotaAtual);

                double custoOtimizado = Miscs.calcularCustoRota(grafo, rotaAtual);
                memeticOffspringList.add(new Pair<>(rotaAtual, custoOtimizado));
            }

            List<Pair<List<Integer>, Double>> vencedoresTorneio = Renovacao.renovarPorTorneio(populationList, memeticOffspringList, 3);

            double somaValores = 0;
            for(Pair<List<Integer>, Double> vencedor: vencedoresTorneio){
                somaValores += vencedor.value();
                if(menorValor == null) {
                    menorValor = vencedor;
                } else if (menorValor.value() > vencedor.value()) {
                    menorValor = vencedor;
                }
            }
            mediaValores = somaValores/vencedoresTorneio.size();

            long finish = System.currentTimeMillis();
            tempoExecucao = finish - start;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}