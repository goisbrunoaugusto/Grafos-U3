package com.ufrn.algorithm;

import com.ufrn.miscs.Miscs;
import com.ufrn.miscs.Pair;
import com.ufrn.model.Grafo;
import org.apache.poi.poifs.property.Parent;

import java.util.*;

public class Crossover {
    /**
     * @param grafo Grafo das rotas
     * @param parentList Lista de pais
     * @return Retorna uma lista de rotas filhas geradas com a união entre dois pais diferentes
     * */
    static public List<Pair<List<Integer>, Double>> onePoint(Grafo grafo, List<Pair<List<Integer>, Double>> parentList){
        Random random = new Random();
        // Ponto de cruzamento
        int point = random.nextInt(0, parentList.getFirst().key().size());

        List<Pair<List<Integer>, Double>> offspringList = new ArrayList<>();
        for (int i = 0; i < parentList.size(); i = i + 2) {
            // Determinação dos itens de cada filho com base no ponto de cruzamento
            List<Integer> parentA = parentList.get(i).key();
            List<Integer> parentB = parentList.get(i+1).key();
            List<Integer> parentAChromosomes = new ArrayList<>(parentA.subList(0,point));
            List<Integer> parentBChromosomes = new ArrayList<>(parentB.subList(point,parentList.get(i+1).key().size()));
            List<Integer> offspringChromosomes = new ArrayList<>(parentAChromosomes);

            // Listagem dos itens que não estão presentes na parte do pai A
            Queue<Integer> notOnListQueue = new LinkedList<>();
            for (int j = 0; j < parentA.size(); j++) {
                if(!offspringChromosomes.contains(j)) notOnListQueue.add(j);
            }

            // Reparação da parte do pai B, removendo itens que já estão em A
            List<Integer> parentBChromosomesReparated = new ArrayList<>(parentBChromosomes);
            for (int j = 0; j < parentBChromosomesReparated.size(); j++) {
                if(parentAChromosomes.contains(parentBChromosomesReparated.get(j))) {
                    boolean added = false;
                    do{
                        if (notOnListQueue.isEmpty()) {
                            break;
                        }

                        if(parentBChromosomesReparated.contains(notOnListQueue.element())) {
                            notOnListQueue.remove();
                        }else{
                            parentBChromosomesReparated.set(j, notOnListQueue.remove());
                            added = true;
                        }
                    }while(!added);
                }

                if (notOnListQueue.isEmpty()) {
                    break;
                }
            }

            // Adição dos itens reparados da parte de B
            offspringChromosomes.addAll(parentBChromosomesReparated);
//            System.out.println(offspringChromosomes);

            // Adição do filho gerado à lista de filhos
            Pair<List<Integer>, Double> offspring = new Pair<>(offspringChromosomes, Miscs.calcularCustoRota(grafo, offspringChromosomes));
            offspringList.add(offspring);

            offspringChromosomes = new ArrayList<>(parentBChromosomes);
            // Listagem dos itens que não estão presentes na parte do pai B
            notOnListQueue = new LinkedList<>();
            for (int j = 0; j < parentB.size(); j++) {
                if(!offspringChromosomes.contains(j)) notOnListQueue.add(j);
            }

            // Reparação da parte do pai A, removendo itens que já estão em B
            List<Integer> parentAChromosomesReparated = new ArrayList<>(parentAChromosomes);
            for (int j = 0; j < parentAChromosomesReparated.size(); j++) {
                if(parentBChromosomes.contains(parentAChromosomesReparated.get(j))) {
                    boolean added = false;
                    do{
                        if (notOnListQueue.isEmpty()) {
                            break;
                        }

                        if(parentAChromosomesReparated.contains(notOnListQueue.element())) {
                            notOnListQueue.remove();
                        }else{
                            parentAChromosomesReparated.set(j, notOnListQueue.remove());
                            added = true;
                        }
                    }while(!added);
                }

                if (notOnListQueue.isEmpty()) {
                    break;
                }
            }

            // Adição dos itens reparados da parte de A
            offspringChromosomes.addAll(parentAChromosomesReparated);
//            System.out.println(offspringChromosomes);
            // Adição do filho gerado à lista de filhos
            offspring = new Pair<>(offspringChromosomes, Miscs.calcularCustoRota(grafo, offspringChromosomes));
            offspringList.add(offspring);

        }

        return offspringList;
    }
}
