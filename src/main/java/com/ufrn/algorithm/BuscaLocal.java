package com.ufrn.algorithm;

import com.ufrn.miscs.Miscs;
import com.ufrn.model.Grafo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BuscaLocal {
    public List<Integer> executarSwap(Grafo grafo, List<Integer> rotaInicial) {
        List<Integer> rota = new ArrayList<>(rotaInicial);

        boolean houveMelhoria = true;
        double ditanciaAtual = Miscs.calcularCustoRota(grafo, rota);

        while (houveMelhoria) {
            houveMelhoria = false;

            for (int i = 1; i < rota.size() - 1; i++) {
                for (int j = i + 1; j < rota.size(); j++) {

                    Collections.swap(rota, i, j);

                    double novoCusto = Miscs.calcularCustoRota(grafo, rota);

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
}
