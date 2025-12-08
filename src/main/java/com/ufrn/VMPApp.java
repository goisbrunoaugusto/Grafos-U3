package com.ufrn;

import com.ufrn.algorithm.BuscaLocal;
import com.ufrn.algorithm.VizinhoMaisProximo;
import com.ufrn.miscs.Miscs;
import com.ufrn.model.Grafo;
import com.ufrn.util.LeitorExcel;

import java.util.List;

public class VMPApp {
    public static void main( String[] args ) {
        try {
            LeitorExcel leitor = new LeitorExcel();
            Grafo grafo = leitor.popularGrafo("/planilha.xlsx", 48, 0);
            System.out.println("Grafo carregado com " + grafo.getNumVertices() + " vértices.");

            System.out.println("\n--- 1. Vizinho Mais Próximo ---");
            VizinhoMaisProximo vizinhoMaisProximo = new VizinhoMaisProximo();
            List<Integer> rotaInicial = vizinhoMaisProximo.resolver(grafo, 0);

            double custoInicial = Miscs.calcularCustoRota(grafo, rotaInicial);
            System.out.println("Rota inicial: " + rotaInicial);
            System.out.printf("Distância inicial: %.2f km\n", custoInicial);

            BuscaLocal bl = new BuscaLocal();

            System.out.println("\n--- 2. Busca Local - Shift ---");
            List<Integer> rotaShift = bl.executarShift(grafo, rotaInicial);
            double custoShift = Miscs.calcularCustoRota(grafo, rotaShift);

            System.out.println("Rota otimizada: " + rotaShift);
            System.out.printf("Distância final: %.2f km\n", custoShift);
            System.out.printf("Melhoria: %.2f%%\n", ((custoInicial - custoShift) / custoInicial) * 100);

            System.out.println("\n--- 3. Busca Local - Swap ---");
            List<Integer> rotaSwap = bl.executarSwap(grafo, rotaInicial);
            double custoSwap = Miscs.calcularCustoRota(grafo, rotaSwap);

            System.out.println("Rota otimizada: " + rotaSwap);
            System.out.printf("Distância final: %.2f km\n", custoSwap);
            System.out.printf("Melhoria: %.2f%%\n", ((custoInicial - custoSwap) / custoInicial) * 100);

            System.out.println("\n--- 4. Busca Local - Inversão ---");
            List<Integer> rotaInversao = bl.executarInversao(grafo, rotaInicial);
            double custoInversao = Miscs.calcularCustoRota(grafo, rotaInversao);

            System.out.println("Rota otimizada: " + rotaInversao);
            System.out.printf("Distância final: %.2f km\n", custoInversao);
            System.out.printf("Melhoria: %.2f%%\n", ((custoInicial - custoInversao) / custoInicial) * 100);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
