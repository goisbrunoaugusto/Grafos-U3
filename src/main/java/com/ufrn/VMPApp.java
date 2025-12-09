package com.ufrn;

import com.ufrn.algorithm.BuscaLocal;
import com.ufrn.algorithm.VizinhoMaisProximo;
import com.ufrn.miscs.Miscs;
import com.ufrn.model.Grafo;
import com.ufrn.util.LeitorExcel;

import java.util.List;

public class VMPApp {
    public static void main(String[] args) {
        System.out.println("=== Heurística do Vizinho mais Próximo + Busca Local ===");

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

            System.out.println("\n--- Vizinho Mais Próximo ---");
            VizinhoMaisProximo vizinhoMaisProximo = new VizinhoMaisProximo();
            List<Integer> rotaInicial = vizinhoMaisProximo.resolver(grafo, 0);
            double custoInicial = Miscs.calcularCustoRota(grafo, rotaInicial);

            System.out.println("Rota inicial: " + rotaInicial);
            System.out.printf("Custo Inicial: %.2f %s\n", custoInicial, unidade);

            BuscaLocal bl = new BuscaLocal();
            System.out.println("\n--- Busca Local (Swap) ---");
            List<Integer> rotaOtimizada = bl.executarSwap(grafo, rotaInicial);
            double custoOtimizado = Miscs.calcularCustoRota(grafo, rotaOtimizada);

            System.out.println("Rota otimizada: " + rotaOtimizada);
            System.out.printf("Custo otimizado: %.2f %s\n", custoOtimizado, unidade);

            System.out.printf("Melhoria: %.2f%%\n", ((custoInicial - custoOtimizado) / custoInicial) * 100);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}