package com.ufrn.algorithm;

import com.ufrn.miscs.Miscs;
import com.ufrn.miscs.Pair;
import com.ufrn.model.Grafo;
import org.apache.poi.poifs.property.Parent;

import java.util.*;

public class Crossover {
    static public List<Pair<List<Integer>, Double>> onePoint(Grafo grafo, List<Pair<List<Integer>, Double>> parentList){
        Random random = new Random();
        int point = random.nextInt(0, parentList.getFirst().key().size());

        List<Pair<List<Integer>, Double>> offspringList = new ArrayList<>();
        for (int i = 0; i < parentList.size(); i = i + 2) {
            List<Integer> parentA = parentList.get(i).key();
            List<Integer> parentB = parentList.get(i+1).key();
            List<Integer> parentAChromosomes = new ArrayList<>(parentA.subList(0,point));
            List<Integer> parentBChromosomes = new ArrayList<>(parentB.subList(point,parentList.get(i+1).key().size()));
            List<Integer> offspringChromosomes = new ArrayList<>(parentAChromosomes);

            Queue<Integer> notOnListQueue = new LinkedList<>();
            for (int j = 0; j < parentA.size(); j++) {
                if(!offspringChromosomes.contains(j)) notOnListQueue.add(j);
            }

            for (int j = 0; j < parentBChromosomes.size(); j++) {
                if(parentAChromosomes.contains(parentBChromosomes.get(j))) {
                    boolean added = false;
                    do{
                        if (notOnListQueue.isEmpty()) {
                            break;
                        }

                        if(parentBChromosomes.contains(notOnListQueue.element())) {
                            notOnListQueue.remove();
                        }else{
                            parentBChromosomes.set(j, notOnListQueue.remove());
                            added = true;
                        }
                    }while(!added);
                }

                if (notOnListQueue.isEmpty()) {
                    break;
                }
            }

            offspringChromosomes.addAll(parentBChromosomes);

            Pair<List<Integer>, Double> offspring = new Pair<>(offspringChromosomes, Miscs.calcularCustoRota(grafo, offspringChromosomes));
            offspringList.add(offspring);
        }

        return offspringList;
    }
}
