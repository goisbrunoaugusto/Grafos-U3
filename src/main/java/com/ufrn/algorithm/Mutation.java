package com.ufrn.algorithm;

import com.ufrn.miscs.Pair;
import com.ufrn.model.Grafo;

import java.util.*;
import java.util.stream.Collectors;

public class Mutation {
    static public List<Pair<List<Integer>, Integer>> mutate(Grafo grafo, List<Pair<List<Integer>, Integer>> originalOffspringList, double percent){
        Random random = new Random();
        int mutatedOffspringSize = (int) Math.floor(originalOffspringList.size()*percent);
        Set<Integer> randomRouteIndexes = random.ints(0, originalOffspringList.size()).distinct().limit(mutatedOffspringSize).boxed().collect(Collectors.toSet());

        List<Pair<List<Integer>, Integer>> mutatedOffspringList = new ArrayList<>(originalOffspringList);
        for(int routeIndex : randomRouteIndexes){
            Collections.swap(mutatedOffspringList.get(routeIndex).key(), random.nextInt(0, originalOffspringList.getFirst().key().size()), random.nextInt(0, originalOffspringList.getFirst().key().size()));
        }

        return mutatedOffspringList;
    }
}
