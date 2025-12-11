package com.ufrn.algorithm;

import com.ufrn.miscs.Miscs;
import com.ufrn.miscs.Pair;
import com.ufrn.model.Grafo;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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
        //Gera uma ordem aleatória de posições que serão mutacionadas caso sejam escolhidas
        List<Integer> randomRouteIndexes = IntStream.range(0, originalOffspringList.size()).boxed().collect(Collectors.toList());
        Collections.shuffle(randomRouteIndexes);

        List<Pair<List<Integer>, Double>> mutatedOffspringList = new ArrayList<>(originalOffspringList);
        for(int i = 0; i < mutatedOffspringList.size(); i++){
            // Verifica se vai ser mutacionado baseado na porcentagem definida antes
            if(random.nextInt(100) < percent*100){
                int randomIndex = randomRouteIndexes.removeFirst();
                //Realiza a mutação
                Collections.swap(mutatedOffspringList.get(randomIndex).key(), random.nextInt(0, originalOffspringList.getFirst().key().size()), random.nextInt(0, originalOffspringList.getFirst().key().size()));

                //Salva a mutação na lista
                Pair<List<Integer>, Double> mutatedOffspring = new Pair<>(mutatedOffspringList.get(randomIndex).key(), Miscs.calcularCustoRota(grafo, mutatedOffspringList.get(randomIndex).key()));
                mutatedOffspringList.set(randomIndex, mutatedOffspring);
            }
        }

        //Retorna a lista
        return mutatedOffspringList;
    }
}
