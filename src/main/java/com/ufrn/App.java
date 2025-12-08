package com.ufrn;

import com.ufrn.algorithm.BuscaLocal;
import com.ufrn.algorithm.VizinhoMaisProximo;
import com.ufrn.miscs.Miscs;
import com.ufrn.model.Grafo;
import com.ufrn.util.LeitorExcel;

import java.util.List;

public class App {
    public static void main( String[] args ) {
        try {
            LeitorExcel leitor = new LeitorExcel();
            Grafo grafo = leitor.popularGrafo("/planilha.xlsx", 0);
            System.out.println("Grafo carregado com " + grafo.getNumVertices() + " vértices.");

            System.out.println("\n--- 1. Vizinho Mais Próximo ---");
            VizinhoMaisProximo vizinho = new VizinhoMaisProximo();
            List<Integer> rotaInicial = vizinho.resolver(grafo, 0);

            double distanciaInicial = Miscs.calcularCustoRota(grafo, rotaInicial);
            System.out.println("Rota inicial: " + rotaInicial);
            System.out.printf("Distância inicial: %.2f km\n", distanciaInicial);

            BuscaLocal bl = new BuscaLocal();
            System.out.println("\n--- 2. Busca Local ---");
            List<Integer> rotaOtimizada = bl.executarSwap(grafo, rotaInicial);
            double distanciaFinal = Miscs.calcularCustoRota(grafo, rotaOtimizada);

            System.out.println("Rota otimizada: " + rotaOtimizada);
            System.out.printf("Distância final: %.2f km\n", distanciaFinal);
            System.out.printf("Melhoria: %.2f%%\n", ((distanciaInicial - distanciaFinal) / distanciaInicial) * 100);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
