package com.ufrn.algorithm;

import com.ufrn.miscs.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Selection {
    /**
     * @param populacao: Lista de pares contendo as rotas e seus respectivos custos
     * @param numPais: Quantidade de indivíduos que serão selecionados para compor a lista de pais
     * @param tamanhoTorneio: Número de indivíduos sorteados aleatoriamente para competir a cada rodada
     * @return Retorna uma lista de pais selecionados com base nas vitórias nos torneios (menor custo vence)
     */
    static public List<Pair<List<Integer>, Double>> selecaoPorTorneio(List<Pair<List<Integer>, Double>> populacao, int numPais, int tamanhoTorneio) {
        Random random = new Random();
        List<Pair<List<Integer>, Double>> pais = new ArrayList<>();

        // Loop para selecionar a quantidade desejada de pais
        for (int i = 0; i < numPais; i++) {
            // Inicia o torneio sorteando o primeiro competidor
            int indiceSorteado = random.nextInt(populacao.size());
            Pair<List<Integer>, Double> vencedorRodada = populacao.get(indiceSorteado);

            // Realiza as batalhas contra os demais competidores
            for (int j = 0; j < tamanhoTorneio - 1; j++) {
                int novoIndiceSorteado = random.nextInt(populacao.size());
                Pair<List<Integer>, Double> candidato = populacao.get(novoIndiceSorteado);

                // Menor custo vence
                if (candidato.value() < vencedorRodada.value()) {
                    vencedorRodada = candidato;
                }
            }

            // Adiciona o vencedor desta rodada à lista de pais
            pais.add(vencedorRodada);
        }

        return pais;
    }

    /**
    * @param population: List of pairs of routes, and their costs sorted by them
      @param percent: Percent of the population that will become parents
      @return returns a list of parents based on the most efficient costs
    * */
    static public List<Pair<List<Integer>, Double>> elitismSelection(List<Pair<List<Integer>, Double>> population, double percent){
        int parentSize = (int) Math.floor(population.size()*percent);
        if (parentSize % 2 != 0) parentSize++;
        if(parentSize <= 0) return null;

        return population.subList(0, parentSize);
    }
}
