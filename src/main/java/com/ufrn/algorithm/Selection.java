package com.ufrn.algorithm;

import com.ufrn.miscs.Pair;

import java.util.List;

public class Selection {
    /**
    * @param population: Lista de rotas ordenada por custo total
      @param percent: Porcentagem da população que vai se tornar um pai
      @return Retorna uma lista com as melhores rotas escolhidas para serem pais.
    * */
    static public List<Pair<List<Integer>, Double>> elitismSelection(List<Pair<List<Integer>, Double>> population, double percent){
        // Determina a quantidade de rotas pais
        int parentSize = (int) Math.floor(population.size()*percent);

        // Caso a quantidade seja ímpar, adiciona um pai a mais
        if (parentSize % 2 != 0) parentSize++;

        if(parentSize <= 0) return null;

        return population.subList(0, parentSize);
    }
}
