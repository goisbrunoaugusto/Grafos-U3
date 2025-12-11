package com.ufrn.algorithm;

import java.util.ArrayList;
import java.util.List;

import com.ufrn.model.Grafo;

public class VizinhoMaisProximo implements AlgoritmoPCV {
    /**
     * @param grafo O objeto Grafo contendo a matriz de adjacência com os custos/distâncias entre as cidades.
     * @param verticeInicial O índice do vértice de partida para o caixeiro viajante.
     * @return Uma lista de inteiros representando a sequência de cidades visitadas, formando um ciclo.
     */
    @Override
    public List<Integer> resolver(Grafo grafo, int verticeInicial) {
        boolean[] visitado = new boolean[grafo.getNumVertices()];
        List<Integer> rota = new ArrayList<>();

        int verticeAtual = verticeInicial;
        visitado[verticeAtual] = true;
        rota.add(verticeAtual);

        double custoTotal = 0.0;

        for (int i = 0; i < grafo.getNumVertices() - 1; i++) {
            int proximoVertice = -1;
            double menorCusto = Double.MAX_VALUE;

            for (int w = 0; w < grafo.getNumVertices(); w++) {
                if (!visitado[w]) {
                    double custo = grafo.getPeso(verticeAtual, w);
                    if (custo < menorCusto) {
                        menorCusto = custo;
                        proximoVertice = w;
                    }
                }
            }

            if (proximoVertice != -1) {
                visitado[proximoVertice] = true;
                rota.add(proximoVertice);
                custoTotal += menorCusto;
                verticeAtual = proximoVertice;
            }
        }

        return rota;
    }
}
