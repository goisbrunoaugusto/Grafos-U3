package com.ufrn.algorithm;

import com.ufrn.miscs.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Renovacao {
    /**
     * @param populacaoAntiga: A lista de pares representando a população da geração atual (pais) e seus custos
     * @param novosFilhos: A lista de pares representando os novos indivíduos gerados (filhos) e seus custos
     * @param tamanhoTorneio: O número de indivíduos sorteados aleatoriamente do conjunto (pais + filhos) para competir por uma vaga na nova geração
     * @return Retorna a nova população preenchida pelos vencedores dos torneios realizados entre o conjunto unificado de pais e filhos
     */
    static public List<Pair<List<Integer>, Double>> renovarPorTorneio(List<Pair<List<Integer>, Double>> populacaoAntiga, List<Pair<List<Integer>, Double>> novosFilhos, int tamanhoTorneio) {
        List<Pair<List<Integer>, Double>> novaPopulacao = new ArrayList<>();

        // Cria a lista com todos os candidatos (união de pais e filhos)
        List<Pair<List<Integer>, Double>> candidatosDisponiveis = new ArrayList<>();
        candidatosDisponiveis.addAll(populacaoAntiga);
        candidatosDisponiveis.addAll(novosFilhos);

        int tamanhoDesejado = populacaoAntiga.size();

        Random random = new Random();
        // Preenche as vagas da nova geração
        while (novaPopulacao.size() < tamanhoDesejado) {
            Pair<List<Integer>, Double> vencedorRodada = null;
            int indiceVencedorNaLista = -1;

            int competidoresAtuais = Math.min(tamanhoTorneio, candidatosDisponiveis.size());

            // Realiza um torneio para decidir uma vaga
            for (int i = 0; i < competidoresAtuais; i++) {
                if (candidatosDisponiveis.isEmpty()) break;

                int indiceSorteado = random.nextInt(candidatosDisponiveis.size());
                Pair<List<Integer>, Double> candidato = candidatosDisponiveis.get(indiceSorteado);

                // O primeiro é o vencedor temporário ou se o candidato atual tiver custo menor que o vencedor atual
                if (vencedorRodada == null || candidato.value() < vencedorRodada.value()) {
                    vencedorRodada = candidato;
                    indiceVencedorNaLista = indiceSorteado;
                }
            }

            // Adiciona o vencedor na nova população
            if (vencedorRodada != null) {
                novaPopulacao.add(vencedorRodada);

                // Remove o vencedor da lista de disponíveis para evitar duplicatas
                if (indiceVencedorNaLista != -1) {
                    candidatosDisponiveis.remove(indiceVencedorNaLista);
                }
            } else {
                // Para o loop se não houver vencedor
                break;
            }
        }

        return novaPopulacao;
    }
}
