package com.ufrn;

import com.ufrn.algorithm.Fitness;
import com.ufrn.miscs.Pair;
import com.ufrn.model.Grafo;
import com.ufrn.util.LeitorExcel;

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

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
