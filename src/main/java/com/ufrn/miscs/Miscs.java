package com.ufrn.miscs;

import com.ufrn.model.Grafo;

import java.util.List;

public class Miscs {

    static public double calcularCustoRota(Grafo grafo, List<Integer> rota) {
        double custo = 0.0;
        for (int i = 0; i < rota.size() - 1; i++) {
            int origem = rota.get(i);
            int destino = rota.get(i + 1);
            custo += grafo.getPeso(origem, destino);
        }

        custo += grafo.getPeso(rota.get(rota.size() - 1), rota.get(0));

        return custo;
    }
}
