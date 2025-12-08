package com.ufrn.algorithm;

import com.ufrn.model.Grafo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BuscaLocal {
    public List<Integer> executarSwap(Grafo grafo, List<Integer> rotaInicial) {
        List<Integer> rota = new ArrayList<>(rotaInicial);

        boolean houveMelhoria = true;
        double ditanciaAtual = calcularCustoRota(grafo, rota);

        while (houveMelhoria) {
            houveMelhoria = false;

            for (int i = 1; i < rota.size() - 1; i++) {
                for (int j = i + 1; j < rota.size(); j++) {

                    Collections.swap(rota, i, j);

                    double novoCusto = calcularCustoRota(grafo, rota);

                    if (novoCusto < ditanciaAtual) {
                        ditanciaAtual = novoCusto;
                        houveMelhoria = true;
                    } else {
                        Collections.swap(rota, i, j);
                    }
                }
            }
        }

        return rota;
    }

    public double calcularCustoRota(Grafo grafo, List<Integer> rota) {
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
