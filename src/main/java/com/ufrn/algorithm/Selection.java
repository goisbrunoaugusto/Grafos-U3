package com.ufrn.algorithm;

import com.ufrn.miscs.Pair;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class Selection {
    /**
    * @param population: Lista de rotas ordenada por custo total
      @param percent: Porcentagem da população que vai se tornar um pai
      @return Retorna uma lista com as melhores rotas escolhidas para serem pais.
    * */
    static public List<Pair<List<Integer>, Double>> elitismSelection(List<Pair<List<Integer>, Double>> population, double percent){

      // Limita a porcentagem máxima a 50%
      if (percent > .5) percent = .5;

      // Determina a quantidade de rotas pais
      int parentSize = (int) Math.floor(population.size()*percent);

      // Caso a quantidade seja ímpar, remove um pai
      if (parentSize % 2 != 0) parentSize--;

      if(parentSize <= 0) return null;

      // População elite
      List<Pair<List<Integer>, Double>> elite = population.subList(0, parentSize);

      // População restante (quantidade igual a da elite)
      List<Pair<List<Integer>, Double>> restPopulation = population.subList(parentSize, elite.size()*2);

      // Embaralha a população restante para seleção aleatória
      Collections.shuffle(restPopulation);

      // Pares de pais (Elite + Aleatório)
      List<Pair<List<Integer>, Double>> parentList = new ArrayList<>();
      for (int i = 0; i < parentSize; i++) {
        parentList.add(elite.get(i));
        parentList.add(restPopulation.get(i));
      }

      return parentList;
    }
}
