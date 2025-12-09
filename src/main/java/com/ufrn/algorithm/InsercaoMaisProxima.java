package com.ufrn.algorithm;

import java.util.ArrayList;
import java.util.List;

import com.ufrn.model.Grafo;

public class InsercaoMaisProxima implements AlgoritmoPCV {

    /**
     * @param grafo O objeto Grafo contendo a matriz de adjacência com os custos/distâncias entre as cidades.
     * @param verticeInicial O índice do vértice de partida para o caixeiro viajante.
     * @return Uma lista de inteiros representando a sequência de cidades visitadas, formando um ciclo.
     */
    @Override
    public List<Integer> resolver(Grafo grafo, int verticeInicial) {
        int numVertices = grafo.getNumVertices();
        boolean[] visitado = new boolean[numVertices];
        List<Integer> rota = new ArrayList<>();
        int cidadesVisitadasCount = 0;
        
        // Marca (s) como visitado
        visitado[verticeInicial] = true;
        cidadesVisitadasCount++;

        // Encontra o vértice v0 mais próximo de s (guloso)
        int v0 = -1;
        double menorDistanciaInicial = Double.MAX_VALUE;

        for (int i = 0; i < numVertices; i++) {
            if (i != verticeInicial) { 
                double dist = grafo.getPeso(verticeInicial, i);
                if (dist < menorDistanciaInicial) {
                    menorDistanciaInicial = dist;
                    v0 = i;
                }
            }
        }

        // Rota inicial: [s, v0, s]
        rota.add(verticeInicial);
        rota.add(v0);
        rota.add(verticeInicial);
        
        // Marca v0 como visitado
        visitado[v0] = true;
        cidadesVisitadasCount++;

        // Enquanto ainda houver cidades para visitar
        while (cidadesVisitadasCount < numVertices) {

            int melhorCidade = -1;
            double menorDistancia = Double.MAX_VALUE;
            // Passo1: Encontra a cidade r não visitada mais próxima do ciclo atual
            for (int r = 0; r < numVertices; r++) {
                if (!visitado[r]) {
                    for (int k = 0; k < rota.size() - 1; k++) {
                        int u = rota.get(k);
                        double dist = grafo.getPeso(u, r);
                        if (dist < menorDistancia) {
                            menorDistancia = dist;
                            melhorCidade = r;
                        }
                    }
                }
            }
            
            int melhorPosicao = -1;
            double menorAumentoCusto = Double.MAX_VALUE;
            // Passo2: Encontra a melhor posição para inserição de r
            for (int i = 0; i < rota.size() - 1; i++) {
                int u = rota.get(i);
                int v = rota.get(i + 1);

                // Custo de remover a aresta direta (u,v) e passar por r
                double custoAtual = grafo.getPeso(u, v);
                double novoCusto = grafo.getPeso(u, melhorCidade) + grafo.getPeso(melhorCidade, v);
                
                double aumento = novoCusto - custoAtual;

                // Se essa inserção for a mais barata encontrada
                if (aumento < menorAumentoCusto) {
                    menorAumentoCusto = aumento;
                    // A posição de inserção é entre u e v (índice i + 1)
                    melhorPosicao = i + 1;
                }
            }

            
            if (melhorCidade != -1) {
                // Inserir r na rota na melhor posição encontrada
                rota.add(melhorPosicao, melhorCidade);
                
                visitado[melhorCidade] = true;
                cidadesVisitadasCount++;
            }
        }

        return rota;
    }
}