package com.ufrn.algorithm;

import com.ufrn.miscs.Pair;

import java.util.List;

public class Selection {
    /**
    * @param population: List of pairs of routes, and their costs sorted by them
      @param percent: Percent of the population that will become parents
      @return returns a list of parents based on the most efficient costs
    * */
    static public List<Pair<List<Integer>, Double>> elitismSelection(List<Pair<List<Integer>, Double>> population, double percent){
        int parentSize = (int) Math.floor(population.size()*percent);
        if(parentSize <= 0) return null;

        return population.subList(0, parentSize-1);
    }
}
