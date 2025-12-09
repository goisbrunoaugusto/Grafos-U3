package com.ufrn.algorithm;

import com.ufrn.miscs.Miscs;
import com.ufrn.miscs.Pair;
import com.ufrn.model.Grafo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class Fitness {

    static private HashMap<Integer, Integer> generateInitialVertexList(int listSize, int max){
        Random generator = new Random();

        HashMap<Integer, Integer> selectedValues = new HashMap<>();
        for (int i = 0; i < listSize; i++) {
            selectedValues.put(i, generator.nextInt(max));
        }

        return selectedValues;
    }

    static public List<Pair<List<Integer>, Double>> setPopulationWithClosestNeighbour(int populationSize, Grafo grafo){
        List<Pair<List<Integer>, Double>> population = new ArrayList<>();

        for (int i = 0; i < populationSize; i++) {
            VizinhoMaisProximo closestNeighbour = new VizinhoMaisProximo();
            List<Integer> route = closestNeighbour.resolver(grafo, i);
            double cost = Miscs.calcularCustoRota(grafo, route);
            population.add(new Pair<>(route, cost));
        }

        return population;
    }

    /***
     *
     * @param populationSize Tamanho desejado da população
     * @param grafo Grafo das rotas
     * @return Uma lista de todas as rotas geradas pelo algoritmo de vizinho mais próximo com o custo total delas
     */
    static public List<Pair<List<Integer>, Double>> setPopulationWithClosestNeighbourAndSwap(int populationSize, Grafo grafo){
        List<Pair<List<Integer>, Double>> population = new ArrayList<>();

        for (int i = 0; i < populationSize; i++) {
            VizinhoMaisProximo closestNeighbour = new VizinhoMaisProximo();
            BuscaLocal bl = new BuscaLocal();

            // Executa o algoritmo de vizinho mais próximo a partir do caminho especificado e aplica a busca local swap na rota gerada
            List<Integer> route = bl.executarSwap(grafo, closestNeighbour.resolver(grafo, i));

            // Cálcula o valor da rota
            double cost = Miscs.calcularCustoRota(grafo, route);

            // Adiciona a rota à população
            population.add(new Pair<>(route, cost));
        }

        return population;
    }

    /***
     *
     * @param populationSize Tamanho desejado da população
     * @param grafo Grafo das rotas
     * @return Uma lista de todas as rotas geradas pelo algoritmo de inserção mais próxima com o custo total delas
     */
    static public List<Pair<List<Integer>, Double>> setPopulationWithClosestInsertionAndShift(int populationSize, Grafo grafo){
        List<Pair<List<Integer>, Double>> population = new ArrayList<>();

        for (int i = 0; i < populationSize; i++) {
            InsercaoMaisProxima closestInsertion = new InsercaoMaisProxima();
            BuscaLocal bl = new BuscaLocal();

            // Executa o algoritmo de inserção mais próxima a partir do caminho especificado
            List<Integer> CIRoute = closestInsertion.resolver(grafo, i);
            // Remove o último valor que forma o ciclo da inserção mais próxima para prevenir bugs durante a execução do algoritmo genético
            CIRoute.removeLast();
            // Aplica a busca local shift na rota gerada
            List<Integer> route = bl.executarShift(grafo, CIRoute);

            // Cálcula o valor da rota
            double cost = Miscs.calcularCustoRota(grafo, route);

            // Adiciona a rota à população
            population.add(new Pair<>(route, cost));
        }

        return population;
    }
}
