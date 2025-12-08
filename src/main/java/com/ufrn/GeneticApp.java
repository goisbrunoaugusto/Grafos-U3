package com.ufrn;

import com.ufrn.algorithm.Crossover;
import com.ufrn.algorithm.Fitness;
import com.ufrn.algorithm.Mutation;
import com.ufrn.algorithm.Selection;
import com.ufrn.miscs.Pair;
import com.ufrn.model.Grafo;
import com.ufrn.util.LeitorExcel;
import org.apache.commons.math3.exception.NullArgumentException;

import java.util.Comparator;
import java.util.List;

public class GeneticApp {
    public static void main( String[] args ) {
        try {
            LeitorExcel leitor = new LeitorExcel();
            Grafo grafo = leitor.popularGrafo("/planilha.xlsx", 48, 0);
            System.out.println("Grafo carregado com " + grafo.getNumVertices() + " vértices.");

            List<Pair<List<Integer>, Double>> populationList = Fitness.setPopulationWithClosestNeighbourAndSwap(grafo.getNumVertices(), grafo);
            populationList.sort(Comparator.comparingDouble(Pair<List<Integer>, Double>::value).reversed());

            for(Pair<List<Integer>, Double> route : populationList){
                System.out.print("Rota: " + route.key());
                System.out.printf(" | Custo: %.2f km\n", route.value());
            }

            System.out.println();
            System.out.println("------------------ Pais selecionados ------------------ ");
            List<Pair<List<Integer>, Double>> parentList = Selection.elitismSelection(populationList,.8);
            if(parentList == null){
                throw new NullArgumentException();
            }

            for(Pair<List<Integer>, Double> parent : parentList){
                System.out.print("Rota: " + parent.key());
                System.out.printf(" | Custo: %.2f km\n", parent.value());
            }

            System.out.println();
            System.out.println("------------------ Filhos gerados ------------------ ");
            List<Pair<List<Integer>, Double>> offspringList = Crossover.onePoint(grafo, parentList);

            for(Pair<List<Integer>, Double> offspring : offspringList){
                System.out.print("Rota: " + offspring.key());
                System.out.printf(" | Custo: %.2f km\n", offspring.value());
            }

            System.out.println();
            System.out.println("------------------ Filhos gerados normais e mutantes ------------------ ");
            List<Pair<List<Integer>, Double>> mutatedOffspringList = Mutation.mutate(grafo, offspringList, .5);

            for(Pair<List<Integer>, Double> offspring : mutatedOffspringList){
                System.out.print("Rota: " + offspring.key());
                System.out.printf(" | Custo: %.2f km\n", offspring.value());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
