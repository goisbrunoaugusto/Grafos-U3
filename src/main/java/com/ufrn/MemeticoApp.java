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
    static double somaMediaValores = 0;
    static double mediaValores = 0;
    static double somaTempoExecucao = 0;
    static double mediaTempoExecucao = 0;

    public static void main( String[] args ) {
        System.out.println("=== Algoritmo Memético ===");
        try {
            Files.write(Paths.get("src/main/java/com/ufrn/results/MemeticoResults.txt"), "".getBytes());

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

            Files.write(Paths.get("src/main/java/com/ufrn/results/MemeticoResults.txt"), text.getBytes(), StandardOpenOption.APPEND);
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

//            long start = System.currentTimeMillis();
            long startTime = System.nanoTime();

            LeitorExcel leitor = new LeitorExcel();
            Grafo grafo = leitor.popularGrafo(planilha, numVertice, aba);

            // Geração da população utilizando vizinho mais próximo com swap e inserção mais próxima com shift
            List<Pair<List<Integer>, Double>> populationList = Fitness.setPopulationWithClosestNeighbourAndSwap(grafo.getNumVertices(), grafo);
            populationList.addAll(Fitness.setPopulationWithClosestInsertionAndShift(grafo.getNumVertices(), grafo));

            // Ordenação da população em ordem crescente de custo total da rota
            populationList.sort(Comparator.comparingDouble(Pair<List<Integer>, Double>::value).reversed());

            // Seleção da população por elitismo (os 80% com menor custo total de rota)
            List<Pair<List<Integer>, Double>> parentList = Selection.elitismSelection(populationList, .8);
            if(parentList == null){
                throw new NullArgumentException();
            }

            // Cruzamento da população selecionada por algoritmo de um ponto
            List<Pair<List<Integer>, Double>> offspringList = Crossover.onePoint(grafo, parentList);

            // Mutação dos filhos gerados em cinquenta por cento
            List<Pair<List<Integer>, Double>> mutatedOffspringList = Mutation.mutate(grafo, offspringList, .5);

            // Aplicação das buscas locais swap, shift e inversão
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

            // Renovação da população por torneio
            List<Pair<List<Integer>, Double>> vencedoresTorneio = Renovacao.renovarPorTorneio(populationList, memeticOffspringList, 3);

            // Definição do menor valor dentre os vencedores do torneio
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

            long stopTime = System.nanoTime();
//            System.out.println("Terminou");
            _tempoExecucao = (double) (stopTime - startTime) / 1_000_000;

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