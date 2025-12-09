package com.ufrn;

import com.ufrn.algorithm.*;
import com.ufrn.miscs.Miscs;
import com.ufrn.miscs.Pair;
import com.ufrn.model.Grafo;
import com.ufrn.util.LeitorExcel;
import org.apache.commons.math3.exception.NullArgumentException;

import java.util.Comparator;
import java.util.List;

public class GeneticApp {
    public static void main( String[] args ) {
        System.out.println("=== Algoritmo Genético ===");

        try {
            String planilha = "/planilha.xlsx";
            String planilhaProblema9e10 = "/planilhaProblema9e10.xlsx";

            rodarProblema(1, planilha, 48, 0);
            rodarProblema(2, planilha, 48, 1);

            rodarProblema(3, planilha, 36, 0);
            rodarProblema(4, planilha, 36, 1);

            rodarProblema(5, planilha, 24, 0);
            rodarProblema(6, planilha, 24, 1);

            rodarProblema(7, planilha, 12, 0);
            rodarProblema(8, planilha,12, 1);

            rodarProblema(9, planilhaProblema9e10, 7, 0);
            rodarProblema(10, planilhaProblema9e10, 7, 1);

            rodarProblema(11, planilha, 6, 0);
            rodarProblema(12, planilha, 6, 1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void rodarProblema(int id, String planilha, int numVertice, int aba) {
        try {
            System.out.println("\n------------------------------------------------------------");
            System.out.println("Problema " + id);

            LeitorExcel leitor = new LeitorExcel();
            Grafo grafo = leitor.popularGrafo(planilha, numVertice, aba);
            String unidade = (aba == 0) ? "km" : "min";

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
                System.out.printf(" | Custo: %.2f %s\n", parent.value(), unidade);
            }

            System.out.println();
            System.out.println("------------------ Filhos gerados ------------------ ");
            List<Pair<List<Integer>, Double>> offspringList = Crossover.onePoint(grafo, parentList);

            for(Pair<List<Integer>, Double> offspring : offspringList){
                System.out.print("Rota: " + offspring.key());
                System.out.printf(" | Custo: %.2f %s\n", offspring.value(), unidade);
            }

            System.out.println();
            System.out.println("------------------ Filhos gerados normais e mutantes ------------------ ");
            List<Pair<List<Integer>, Double>> mutatedOffspringList = Mutation.mutate(grafo, offspringList, .5);

            for(Pair<List<Integer>, Double> offspring : mutatedOffspringList){
                System.out.print("Rota: " + offspring.key());
                System.out.printf(" | Custo: %.2f %s\n", offspring.value(), unidade);
            }

            System.out.println();
            System.out.println("------------------ Filhos que sobraram da renovação ------------------ ");
            List<Pair<List<Integer>, Double>> vencedoresTorneio = Renovacao.renovarPorTorneio(populationList, offspringList, 3);

            for(Pair<List<Integer>, Double> vencedores : vencedoresTorneio){
                System.out.print("Rota: " + vencedores.key());
                System.out.printf(" | Custo: %.2f %s\n", vencedores.value(), unidade);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
