package com.ufrn;

import com.ufrn.algorithm.BuscaLocal;
import com.ufrn.algorithm.InsercaoMaisProxima;
import com.ufrn.miscs.Miscs;
import com.ufrn.model.Grafo;
import com.ufrn.util.LeitorExcel;

import java.util.List;

public class IMPApp {
    public static void main( String[] args ) {
        try {
            String planilha = "/planilha.xlsx";
            String planilhaProblema9e10 = "/planilhaProblema9e10.xlsx";

            rodarProblema(planilha, 48, 0);
            rodarProblema(planilha, 48, 1);

            rodarProblema(planilha, 36, 0);
            rodarProblema(planilha, 36, 1);

            rodarProblema(planilha, 24, 0);
            rodarProblema(planilha, 24, 1);

            rodarProblema(planilha, 12, 0);
            rodarProblema(planilha,12, 1);

            rodarProblema(planilhaProblema9e10, 7, 0);
            rodarProblema(planilhaProblema9e10, 7, 1);

            rodarProblema(planilha, 6, 0);
            rodarProblema(planilha, 6, 1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void rodarProblema(String planilha, int numVertice, int aba){
        try {
            LeitorExcel leitor = new LeitorExcel();
            Grafo grafo = leitor.popularGrafo("/planilha.xlsx", numVertice, aba);
            System.out.println("Grafo carregado com " + grafo.getNumVertices() + " vértices.");

            System.out.println("\n--- 1. Insercao Mais Próxima ---");
            InsercaoMaisProxima insercaoMaisProxima = new InsercaoMaisProxima();
            List<Integer> rotaInicial = insercaoMaisProxima.resolver(grafo, 0);

            double distanciaInicial = Miscs.calcularCustoRota(grafo, rotaInicial);
            System.out.println("Rota inicial: " + rotaInicial);
            if (aba == 0)
                System.out.printf("Distância final: %.2f km\n", distanciaInicial);
            else{
                System.out.printf("Tempo final: %.2f min\n", distanciaInicial);
            }

            BuscaLocal bl = new BuscaLocal();
            System.out.println("\n--- 2. Busca Local ---");
            List<Integer> rotaOtimizada = bl.executarShift(grafo, rotaInicial);
            double distanciaFinal = Miscs.calcularCustoRota(grafo, rotaOtimizada);

            System.out.println("Rota otimizada: " + rotaOtimizada);
            
            if (aba == 0)
                System.out.printf("Distância final: %.2f km\n", distanciaFinal);
            else{
                System.out.printf("Tempo final: %.2f min\n", distanciaFinal);
            }
            System.out.printf("Melhoria: %.2f%%\n", ((distanciaInicial - distanciaFinal) / distanciaInicial) * 100);
            System.out.println("---------------------------------------");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
