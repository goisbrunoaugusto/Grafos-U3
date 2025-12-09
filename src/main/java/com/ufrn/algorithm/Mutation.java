package com.ufrn.algorithm;

import com.ufrn.miscs.Miscs;
import com.ufrn.miscs.Pair;
import com.ufrn.model.Grafo;

import java.util.*;
import java.util.stream.Collectors;

public class Mutation {
    /**
     *
     * @param grafo Grafo das rotas
     * @param originalOffspringList Lista dos filhos original
     * @param percent Porcentagem de filhos que serão mutacionados
     * @return Retorna uma lista contendo filhos que não foram mutacionados junto com filhos que foram
     */
    static public List<Pair<List<Integer>, Double>> mutate(Grafo grafo, List<Pair<List<Integer>, Double>> originalOffspringList, double percent){
        Random random = new Random();
        int mutatedOffspringSize = (int) Math.floor(originalOffspringList.size()*percent);
        Set<Integer> randomRouteIndexes = random.ints(0, originalOffspringList.size()).distinct().limit(mutatedOffspringSize).boxed().collect(Collectors.toSet());

        List<Pair<List<Integer>, Double>> mutatedOffspringList = new ArrayList<>(originalOffspringList);
        for(int routeIndex : randomRouteIndexes){
            Collections.swap(mutatedOffspringList.get(routeIndex).key(), random.nextInt(0, originalOffspringList.getFirst().key().size()), random.nextInt(0, originalOffspringList.getFirst().key().size()));
            Pair<List<Integer>, Double> mutatedOffspring = new Pair<>(mutatedOffspringList.get(routeIndex).key(), Miscs.calcularCustoRota(grafo, mutatedOffspringList.get(routeIndex).key()));

            mutatedOffspringList.set(routeIndex, mutatedOffspring);
        }

        return mutatedOffspringList;
    }
}
