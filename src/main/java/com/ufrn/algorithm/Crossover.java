package com.ufrn.algorithm;

import com.ufrn.miscs.Miscs;
import com.ufrn.miscs.Pair;
import com.ufrn.model.Grafo;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Crossover {
    static public List<Pair<List<Integer>, Double>> onePoint(Grafo grafo, List<Pair<List<Integer>, Double>> parents){
        Random random = new Random();
        int point = random.nextInt(0, parents.getFirst().key().size());

        List<Pair<List<Integer>, Double>> offspringList = new ArrayList<>();
        for (int i = 0; i < parents.size(); i = i + 2) {
            List<Integer> parentA = parents.get(i).key();
            List<Integer> parentB = parents.get(i+1).key();
            List<Integer> parentAChromosomes = new ArrayList<>(parentA.subList(0,point-1));
            List<Integer> parentBChromosomes = new ArrayList<>(parentB.subList(point,parents.get(i+1).key().size()-1));
            List<Integer> offspringChromosomes = new ArrayList<>(parentAChromosomes);
            offspringChromosomes.addAll(parentBChromosomes);

            Pair<List<Integer>, Double> offspring = new Pair<>(offspringChromosomes, Miscs.calcularCustoRota(grafo, offspringChromosomes));
            offspringList.add(offspring);
        }

        return offspringList;
    }
}
