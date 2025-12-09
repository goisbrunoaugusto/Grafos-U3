package com.ufrn.algorithm;

import com.ufrn.miscs.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Selection {
    static public List<Pair<List<Integer>, Double>> tournamentSelection(List<Pair<List<Integer>, Double>> population, int numParents, int k) {
        Random random = new Random();
        List<Pair<List<Integer>, Double>> parents = new ArrayList<>();

        // Loop para selecionar a quantidade desejada de pais
        for (int i = 0; i < numParents; i++) {
            // Inicia o torneio com aleatório
            int randomIndex = random.nextInt(population.size());
            Pair<List<Integer>, Double> winner = population.get(randomIndex);

            // Realiza as batalhas
            for (int j = 0; j < k - 1; j++) {
                int challengerIndex = random.nextInt(population.size());
                Pair<List<Integer>, Double> challenger = population.get(challengerIndex);

                // Menor custo vence
                if (challenger.value() < winner.value()) {
                    winner = challenger;
                }
            }

            // Adiciona vencedor à lista de pais
            parents.add(winner);
        }

        return parents;
    }

    /**
    * @param population: List of pairs of routes, and their costs sorted by them
      @param percent: Percent of the population that will become parents
      @return returns a list of parents based on the most efficient costs
    * */
    static public List<Pair<List<Integer>, Double>> elitismSelection(List<Pair<List<Integer>, Double>> population, double percent){
        int parentSize = (int) Math.floor(population.size()*percent);
        if (parentSize % 2 != 0) parentSize++;
        if(parentSize <= 0) return null;

        return population.subList(0, parentSize);
    }
}
