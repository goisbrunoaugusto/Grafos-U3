package com.ufrn.algorithm;

import com.ufrn.miscs.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Renovacao {
    /**
     * @param populacaoAntiga: A lista de pares representando a população da geração atual (pais) e seus custos
     * @param novosFilhos: A lista de pares representando os novos indivíduos gerados (filhos) e seus custos
     * @param tamanhoTorneio: O número de indivíduos sorteados aleatoriamente do pool (pais + filhos) para competir por uma vaga na nova geração
     * @return Retorna a nova população preenchida pelos vencedores dos torneios realizados entre o conjunto unificado de pais e filhos
     */
    static public List<Pair<List<Integer>, Double>> renovarPorTorneio(List<Pair<List<Integer>, Double>> populacaoAntiga, List<Pair<List<Integer>, Double>> novosFilhos, int tamanhoTorneio) {
        List<Pair<List<Integer>, Double>> novaPopulacao = new ArrayList<>();

        // Cria a pool com todos os candidatos
        List<Pair<List<Integer>, Double>> poolCandidatos = new ArrayList<>();
        poolCandidatos.addAll(populacaoAntiga);
        poolCandidatos.addAll(novosFilhos);

        int tamanhoDesejado = populacaoAntiga.size();

        Random random = new Random();
        // Preenche as vagas da nova geração
        while (novaPopulacao.size() < tamanhoDesejado) {
            Pair<List<Integer>, Double> vencedorRodada = null;
            int indiceVencedorNoPool = -1;

            int competidoresAtuais = Math.min(tamanhoTorneio, poolCandidatos.size());

            // Realiza um torneio para decidir uma vaga
            for (int i = 0; i < competidoresAtuais; i++) {
                if (poolCandidatos.isEmpty()) break;

                int indiceSorteado = random.nextInt(poolCandidatos.size());
                Pair<List<Integer>, Double> candidato = poolCandidatos.get(indiceSorteado);

                // O primeiro é o vencedor temporário ou se o candidato atual tiver custo menor que o vencedor atual
                if (vencedorRodada == null || candidato.value() < vencedorRodada.value()) {
                    vencedorRodada = candidato;
                    indiceVencedorNoPool = indiceSorteado;
                }
            }

            // Adiciona o vencedor na nova população
            if (vencedorRodada != null) {
                novaPopulacao.add(vencedorRodada);

                // Remove o vencedor da pool para evitar duplicatas
                if (indiceVencedorNoPool != -1) {
                    poolCandidatos.remove(indiceVencedorNoPool);
                }
            } else {
                // Para o loop se não houver vencedor
                break;
            }
        }

        return novaPopulacao;
    }
}
