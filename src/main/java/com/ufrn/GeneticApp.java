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
import java.util.ArrayList;
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

            int qtdGen = 1000;

            for (int i = 0; i < 20; i++) {
                rodarProblema(planilha, 48, 0, qtdGen);
            }
            printResultados("Problema 1", "km");

            for (int i = 0; i < 20; i++) {
                rodarProblema(planilha, 48, 1, qtdGen);
            }
            printResultados("Problema 2", "min");

            for (int i = 0; i < 20; i++) {
                rodarProblema(planilha, 36, 0, qtdGen);
            }
            printResultados("Problema 3", "km");

            for (int i = 0; i < 20; i++) {
                rodarProblema(planilha, 36, 1, qtdGen);
            }
            printResultados("Problema 4", "min");

            for (int i = 0; i < 20; i++) {
                rodarProblema(planilha, 24, 0, qtdGen);
            }
            printResultados("Problema 5", "km");

            for (int i = 0; i < 20; i++) {
                rodarProblema(planilha, 24, 1, qtdGen);
            }
            printResultados("Problema 6", "min");

            for (int i = 0; i < 20; i++) {
                rodarProblema(planilha, 12, 0, qtdGen);
            }
            printResultados("Problema 7", "km");

            for (int i = 0; i < 20; i++) {
                rodarProblema(planilha, 12, 1, qtdGen);
            }
            printResultados("Problema 8", "min");

            for (int i = 0; i < 20; i++) {
                rodarProblema(planilhaProblema9e10, 7, 0, qtdGen);
            }
            printResultados("Problema 9", "km");

            for (int i = 0; i < 20; i++) {
                rodarProblema(planilhaProblema9e10, 7, 1, qtdGen);
            }
            printResultados("Problema 10", "min");

            for (int i = 0; i < 20; i++) {
                rodarProblema(planilha, 6, 0, qtdGen);
            }
            printResultados("Problema 11", "km");

            for (int i = 0; i < 20; i++) {
                rodarProblema(planilha, 6, 1, qtdGen);
            }
            printResultados("Problema 12", "min");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void printResultados(String problema, String unidade) {
        try{
            mediaTempoExecucao = somaTempoExecucao/20;
            mediaValores = somaMediaValores/20;

            String text = "------------------------------------------------------------\n" +
                    problema + "\n" +
                    "Menor valor: " + String.format("%.2f", menorValor.value()) + unidade + "\n" +
                    "Média: " + String.format("%.2f", mediaValores) + unidade + "\n" +
                    "Média do tempo de execução: " + String.format("%.2f", mediaTempoExecucao) + "ms\n";
            System.out.println(text);

            Files.write(Paths.get("src/main/java/com/ufrn/results/GeneticResults.txt"), text.getBytes(), StandardOpenOption.APPEND);

            menorValor = null;
            somaMediaValores = 0;
            mediaValores = 0;
            somaTempoExecucao = 0;
            mediaTempoExecucao = 0;
        }catch (IOException e){
            e.printStackTrace();
        }

    }

    public static void rodarProblema(String planilha, int numVertice, int aba, int qtdGen) {
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

            long startTime = System.nanoTime();

            // Geração da população utilizando vizinho mais próximo com swap e inserção mais próxima com shift e totalmente aleatório
            List<Pair<List<Integer>, Double>> populationList = Fitness.setPopulationWithClosestNeighbourAndSwap(grafo.getNumVertices(), grafo);
            populationList.addAll(Fitness.setPopulationWithClosestInsertionAndShift(grafo.getNumVertices(), grafo));
            populationList.addAll(Fitness.setPopulationFullRandom(grafo.getNumVertices(), grafo));
//            System.out.println();
//            for(Pair<List<Integer>, Double> pair : randomPop){
//                System.out.println(pair);
//            }

            // Ordenação da população em ordem crescente de custo total da rota
            populationList.sort(Comparator.comparingDouble(Pair<List<Integer>, Double>::value).reversed());
            List<Pair<List<Integer>, Double>> populacaoFinal = new ArrayList<>(populationList);
            int count = 1;
            do{

//                for(Pair<List<Integer>, Double> route : populacaoFinal){
//                    System.out.print("Rota: " + route.key());
//                    System.out.printf(" | Custo: %.2f km\n", route.value());
//                }
//
//                System.out.println();
//                System.out.println("------------------ Pais selecionados ------------------ ");

                // Seleção da população por elitismo
                List<Pair<List<Integer>, Double>> parentList = Selection.elitismSelection(populacaoFinal,.3);
                if(parentList == null){
                    throw new NullArgumentException();
                }

//                for(Pair<List<Integer>, Double> parent : listaPais){
//                    System.out.print("Rota: " + parent.key());
//                    System.out.printf(" | Custo: %.2f %s\n", parent.value(), unidade);
//                }
//
//                System.out.println();
//                System.out.println("------------------ Filhos gerados ------------------ ");

                // Cruzamento da população selecionada por algoritmo de um ponto
                List<Pair<List<Integer>, Double>> offspringList = Crossover.onePoint(grafo, parentList);

//                for(Pair<List<Integer>, Double> offspring : offspringList){
//                    System.out.print("Rota: " + offspring.key());
//                    System.out.printf(" | Custo: %.2f %s\n", offspring.value(), unidade);
//                }
//
//                System.out.println();
//                System.out.println("------------------ Filhos gerados normais e mutantes ------------------ ");

                // Mutação dos filhos gerados em cinquenta por cento
                List<Pair<List<Integer>, Double>> mutatedOffspringList = Mutation.mutate(grafo, offspringList, .03);

//                for(Pair<List<Integer>, Double> offspring : mutatedOffspringList){
//                    System.out.print("Rota: " + offspring.key());
//                    System.out.printf(" | Custo: %.2f %s\n", offspring.value(), unidade);
//                }
//
//                System.out.println();
//                System.out.println("------------------ Filhos que sobraram da renovação ------------------ ");

                // Renovação da população por torneio
                populacaoFinal  = Renovacao.renovarPorTorneio(populacaoFinal, mutatedOffspringList, 3);
//                for(Pair<List<Integer>, Double> vencedores : populacaoFinal){
//                    System.out.print("Rota: " + vencedores.key());
//                    System.out.printf(" | Custo: %.2f %s\n", vencedores.value(), unidade);
//                }
//                System.out.println(populacaoFinal.size());
//
//                System.out.println();
            }while(count++<=qtdGen);

            long stopTime = System.nanoTime();

            double _somaValores = 0;

            // Definição do menor valor da população final
            for(Pair<List<Integer>, Double> individuo : populacaoFinal){
                _somaValores += individuo.value();
                if(_menorValor == null) {
                    _menorValor = individuo;
                } else if (_menorValor.value() > individuo.value()) {
                    _menorValor = individuo;
                }
            }

            // Definição dos valores que serão usados para comparação

            _tempoExecucao = (double) (stopTime - startTime) / 1_000_000;

            _mediaValores = _somaValores/populacaoFinal.size();
            somaMediaValores += _mediaValores;
            somaTempoExecucao += _tempoExecucao;
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
