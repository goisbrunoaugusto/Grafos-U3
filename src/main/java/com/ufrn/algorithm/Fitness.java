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
        HashMap<Integer, Integer> initialVertexList = generateInitialVertexList(populationSize, grafo.getNumVertices());

        for (int i = 0; i < populationSize; i++) {
            VizinhoMaisProximo closestNeighbour = new VizinhoMaisProximo();
            List<Integer> route = closestNeighbour.resolver(grafo, i);
            double cost = Miscs.calcularCustoRota(grafo, route);
            population.add(new Pair<>(route, cost));
        }

        return population;
    }

    static public List<Pair<List<Integer>, Double>> setPopulationWithClosestNeighbourAndSwap(int populationSize, Grafo grafo){
        List<Pair<List<Integer>, Double>> population = new ArrayList<>();

        for (int i = 0; i < populationSize; i++) {
            VizinhoMaisProximo closestNeighbour = new VizinhoMaisProximo();
            BuscaLocal bl = new BuscaLocal();
            List<Integer> route = bl.executarSwap(grafo, closestNeighbour.resolver(grafo, i));
            double cost = Miscs.calcularCustoRota(grafo, route);
            population.add(new Pair<>(route, cost));
        }

        return population;
    }
}
