package com.ufrn;

import com.ufrn.algorithm.*;
import com.ufrn.algorithm.Selection;
import com.ufrn.miscs.Miscs;
import com.ufrn.miscs.Pair;
import com.ufrn.model.Grafo;
import com.ufrn.util.LeitorExcel;
import org.apache.commons.math3.exception.NullArgumentException;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Comparator;
import java.util.List;

public class GeneticApp {
    static Pair<List<Integer>,Double> menorValor = null;
    static double somaMediaValores = 0;
    static double mediaValores = 0;
    static double somaTempoExecucao = 0;
    static double mediaTempoExecucao = 0;

    public static void main( String[] args ) {
        System.out.println("=== Algoritmo Genético ===");

        try {
            Files.write(Paths.get("src/main/java/com/ufrn/results/GeneticResults.txt"), "".getBytes());

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
            mediaTempoExecucao = somaTempoExecucao/20;
            somaTempoExecucao = 0;
            mediaValores = somaMediaValores/20;
            somaMediaValores = 0;

            String text = "------------------------------------------------------------\n" +
                    problema + "\n" +
                    "Menor valor: " + String.format("%.2f", menorValor.value()) + unidade + "\n" +
                    "Média: " + String.format("%.2f", mediaValores) + unidade + "\n" +
                    "Média do tempo de execução: " + String.format("%.2f", mediaTempoExecucao) + "ms\n";
            System.out.println(text);

            Files.write(Paths.get("src/main/java/com/ufrn/results/GeneticResults.txt"), text.getBytes(), StandardOpenOption.APPEND);
        }catch (IOException e){
            e.printStackTrace();
        }

    }

    public static void rodarProblema(int id, String planilha, int numVertice, int aba) {
        try {
            Pair<List<Integer>, Double> _menorValor = null;
            double _mediaValores = 0;
            double _tempoExecucao = 0;
            String unidade = (aba == 0) ? "km" : "min";

//            System.out.println("\n------------------------------------------------------------");
//            System.out.println("Problema " + id);
//            long start = System.currentTimeMillis();

            LeitorExcel leitor = new LeitorExcel();
            Grafo grafo = leitor.popularGrafo(planilha, numVertice, aba);

//            System.out.println("Começou");
            long startTime = System.nanoTime();

            List<Pair<List<Integer>, Double>> populationList = Fitness.setPopulationWithClosestNeighbourAndSwap(grafo.getNumVertices(), grafo);
            populationList.addAll(Fitness.setPopulationWithClosestInsertionAndShift(grafo.getNumVertices(), grafo));
            populationList.sort(Comparator.comparingDouble(Pair<List<Integer>, Double>::value).reversed());

//            for(Pair<List<Integer>, Double> route : populationList){
//                System.out.print("Rota: " + route.key());
//                System.out.printf(" | Custo: %.2f km\n", route.value());
//            }

//            System.out.println();
//            System.out.println("------------------ Pais selecionados ------------------ ");
            List<Pair<List<Integer>, Double>> parentList = Selection.elitismSelection(populationList,.8);
            if(parentList == null){
                throw new NullArgumentException();
            }

//            for(Pair<List<Integer>, Double> parent : parentList){
//                System.out.print("Rota: " + parent.key());
//                System.out.printf(" | Custo: %.2f %s\n", parent.value(), unidade);
//            }

//            System.out.println();
//            System.out.println("------------------ Filhos gerados ------------------ ");
            List<Pair<List<Integer>, Double>> offspringList = Crossover.onePoint(grafo, parentList);

//            for(Pair<List<Integer>, Double> offspring : offspringList){
//                System.out.print("Rota: " + offspring.key());
//                System.out.printf(" | Custo: %.2f %s\n", offspring.value(), unidade);
//            }

//            System.out.println();
//            System.out.println("------------------ Filhos gerados normais e mutantes ------------------ ");
            List<Pair<List<Integer>, Double>> mutatedOffspringList = Mutation.mutate(grafo, offspringList, .5);

//            for(Pair<List<Integer>, Double> offspring : mutatedOffspringList){
//                System.out.print("Rota: " + offspring.key());
//                System.out.printf(" | Custo: %.2f %s\n", offspring.value(), unidade);
//            }

//            System.out.println();
//            System.out.println("------------------ Filhos que sobraram da renovação ------------------ ");
            List<Pair<List<Integer>, Double>> vencedoresTorneio = Renovacao.renovarPorTorneio(populationList, mutatedOffspringList, 3);

            double _somaValores = 0;
            for(Pair<List<Integer>, Double> vencedor: vencedoresTorneio){
                _somaValores += vencedor.value();
                if(_menorValor == null) {
                    _menorValor = vencedor;
                } else if (_menorValor.value() > vencedor.value()) {
                    _menorValor = vencedor;
                }
            }
            _mediaValores = _somaValores/vencedoresTorneio.size();

//            for(Pair<List<Integer>, Double> vencedores : vencedoresTorneio){
//                System.out.print("Rota: " + vencedores.key());
//                System.out.printf(" | Custo: %.2f %s\n", vencedores.value(), unidade);
//            }

            long stopTime = System.nanoTime();
//            System.out.println("Terminou");
            _tempoExecucao = (double) (stopTime - startTime) / 1_000_000;

            somaMediaValores += _mediaValores;
            somaTempoExecucao += _tempoExecucao;
//            menorValor = _menorValor;
            if(menorValor == null) {
                menorValor = _menorValor;
            } else if (menorValor.value() > _menorValor.value()) {
                menorValor = _menorValor;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
