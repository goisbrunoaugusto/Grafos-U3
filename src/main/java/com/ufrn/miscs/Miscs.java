package com.ufrn.miscs;

import com.ufrn.model.Grafo;

import java.util.ArrayList;
import java.util.List;

public class Miscs {

    /***
     *
     * @param grafo Grafo das rotas
     * @param rota rota que vai ser calculada
     * @return Retorna o custo total da rota
     */
    static public double calcularCustoRota(Grafo grafo, List<Integer> rota) {
        double custo = 0.0;
        // Realiza as somas dos pesos
        for (int i = 0; i < rota.size() - 1; i++) {
            int origem = rota.get(i);
            int destino = rota.get(i + 1);
            custo += grafo.getPeso(origem, destino);
        }

        // Soma o custo entre a última cidade e a primeira ao custo total da rota
        custo += grafo.getPeso(rota.get(rota.size() - 1), rota.get(0));

        return custo;
    }

    public static List<Integer> clonarLista(List<Integer> original) {
        return new ArrayList<>(original);
    }
}
