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

    public List<Integer> executarShift(Grafo grafo, List<Integer> rotaInicial) {
        List<Integer> rota = new ArrayList<>(rotaInicial);

        boolean houveMelhoria = true;
        double distanciaAtual = Miscs.calcularCustoRota(grafo, rota);

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
                    if (novoCusto < distanciaAtual) {
                        distanciaAtual = novoCusto;
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
}
