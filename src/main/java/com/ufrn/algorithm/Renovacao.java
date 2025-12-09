package com.ufrn.algorithm;

import com.ufrn.miscs.Pair;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

public class Renovacao {
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

    public List<Pair<List<Integer>, Double>> renovarPorElitismo(List<Pair<List<Integer>, Double>> populacaoAntiga, List<Pair<List<Integer>, Double>> novosFilhos) {
        int tamanhoDesejado = populacaoAntiga.size();

        // Cria uma lista com todos os indivíduos
        List<Pair<List<Integer>, Double>> poolGeral = new ArrayList<>();
        poolGeral.addAll(populacaoAntiga);
        poolGeral.addAll(novosFilhos);

        // Ordena a pool pelo custo
        poolGeral.sort(Comparator.comparingDouble(Pair::value));

        // Seleciona os melhores para manter o tamanho da população
        int limite = Math.min(tamanhoDesejado, poolGeral.size());

        // Retorna a sublista com os melhores
        return new ArrayList<>(poolGeral.subList(0, limite));
    }
}
