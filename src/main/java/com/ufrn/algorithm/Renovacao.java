package com.ufrn.algorithm;

import com.ufrn.miscs.Miscs;
import com.ufrn.model.Grafo;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Renovacao {
    private Random random = new Random();

    public List<List<Integer>> renovarPorTorneio(List<List<Integer>> populacaoAntiga, List<List<Integer>> novosFilhos, Grafo grafo, int tamanhoTorneio) {
        List<List<Integer>> novaPopulacao = new ArrayList<>();

        // Cria o pool com todos os candidatos
        List<List<Integer>> poolCandidatos = new ArrayList<>();
        poolCandidatos.addAll(populacaoAntiga);
        poolCandidatos.addAll(novosFilhos);

        int tamanhoDesejado = populacaoAntiga.size();

        // Preenche as vagas da nova geração rodando torneios
        while (novaPopulacao.size() < tamanhoDesejado) {
            List<Integer> vencedorRodada = null;
            double melhorCustoRodada = Double.MAX_VALUE;
            int indiceVencedorNoPool = -1;

            // Realiza um torneio para decidir uma vaga
            for (int i = 0; i < tamanhoTorneio; i++) {
                if (poolCandidatos.isEmpty()) break;

                int indiceSorteado = random.nextInt(poolCandidatos.size());
                List<Integer> candidato = poolCandidatos.get(indiceSorteado);
                double custo = Miscs.calcularCustoRota(grafo, candidato);

                if (custo < melhorCustoRodada) {
                    melhorCustoRodada = custo;
                    vencedorRodada = candidato;
                    indiceVencedorNoPool = indiceSorteado;
                }
            }

            // Adiciona o vencedor na nova população
            if (vencedorRodada != null) {
                novaPopulacao.add(Miscs.clonarLista(vencedorRodada));

                // Remove o vencedor da pool para evitar duplicatas
                if (indiceVencedorNoPool != -1) {
                    poolCandidatos.remove(indiceVencedorNoPool);
                }
            }
        }

        return novaPopulacao;
    }
}
