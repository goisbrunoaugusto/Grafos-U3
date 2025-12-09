package com.ufrn.algorithm;

import com.ufrn.miscs.Miscs;
import com.ufrn.model.Grafo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BuscaLocal {
    /**
     * @param grafo O objeto Grafo contendo a matriz de custos/distâncias.
     * @param rotaInicial A lista de inteiros representando a rota inicial.
     * @return Uma nova lista de inteiros contendo a rota otimizada após a aplicação da busca local.
     */
    public List<Integer> executarShift(Grafo grafo, List<Integer> rotaInicial) {
        List<Integer> rota = new ArrayList<>(rotaInicial);
        boolean houveMelhoria = true;
        double custoAtual = Miscs.calcularCustoRota(grafo, rota);

        while (houveMelhoria) {
            houveMelhoria = false;

            // Seleciona o vértice a ser movido
            // Desconsidera a primeira e última posição para não afetar o ciclo
            for (int i = 1; i < rota.size() - 1; i++) {

                // Seleciona a nova posição para o vértice
                for (int j = 1; j < rota.size() - 1; j++) {

                    if (i == j) continue; // Desconsidera mesma posição

                    // Shift (remove de i e insere em j)
                    Integer cidade = rota.remove(i);
                    rota.add(j, cidade);

                    double novoCusto = Miscs.calcularCustoRota(grafo, rota);

                    // Avalia se houve melhoria no custo da rota
                    if (novoCusto < custoAtual) {
                        custoAtual = novoCusto;
                        houveMelhoria = true;

                        // Reinicia o while já que modificamos a rota (First-improvement)
                        break;
                    } else {
                        // Se não houve melhoria, desfaz a mudança
                        rota.remove(j);
                        rota.add(i, cidade);
                    }
                }

                // Reinicia o while já que modificamos a rota (First-improvement)
                if (houveMelhoria) {
                    break;
                }
            }
        }

        return rota;
    }

    public List<Integer> executarSwap(Grafo grafo, List<Integer> rotaInicial) {
        List<Integer> rota = new ArrayList<>(rotaInicial);
        boolean houveMelhoria = true;
        double custoAtual = Miscs.calcularCustoRota(grafo, rota);

        while (houveMelhoria) {
            houveMelhoria = false;

            for (int i = 1; i < rota.size() - 1; i++) {
                for (int j = i + 1; j < rota.size(); j++) {
                    // Realiza a troca entre i e j
                    Collections.swap(rota, i, j);
                    double novoCusto = Miscs.calcularCustoRota(grafo, rota);

                    // Avalia se houve melhoria no custo da rota
                    if (novoCusto < custoAtual) {
                        custoAtual = novoCusto;
                        houveMelhoria = true;
                    } else {
                        // Se não houve melhoria, desfaz a mudança
                        Collections.swap(rota, i, j);
                    }
                }
            }
        }

        return rota;
    }

    public List<Integer> executarInversao(Grafo grafo, List<Integer> rotaInicial) {
        List<Integer> rota = new ArrayList<>(rotaInicial);
        boolean houveMelhoria = true;
        double custoAtual = Miscs.calcularCustoRota(grafo, rota);

        while (houveMelhoria) {
            houveMelhoria = false;

            for (int i = 1; i < rota.size() - 1; i++) {
                for (int j = i + 1; j < rota.size(); j++) {
                    // Inverte trecho entre i e j
                    inverterTrecho(rota, i, j);
                    double novoCusto = Miscs.calcularCustoRota(grafo, rota);

                    // Avalia se houve melhoria no custo da rota
                    if (novoCusto < custoAtual) {
                        custoAtual = novoCusto;
                        houveMelhoria = true;
                    } else {
                        // Se não houve melhoria, desfaz a mudança
                        inverterTrecho(rota, i, j);
                    }
                }
            }
        }

        return rota;
    }

    private void inverterTrecho(List<Integer> rota, int inicio, int fim) {
        while (inicio < fim) {
            Collections.swap(rota, inicio, fim);
            inicio++;
            fim--;
        }
    }
}
