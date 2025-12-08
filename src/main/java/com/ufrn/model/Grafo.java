package com.ufrn.model;

public class Grafo {
    private int numVertices;
    private float[][] matrizAdjacencia; // A matriz está começando do 0 então sempre que for usá-la, subtrair 1 do índice do vértice

    public Grafo(int numVertices) {
        this.numVertices = numVertices;
        this.matrizAdjacencia = new float[numVertices][numVertices];
    }

    public void adicionarAresta(int origem, int destino, float peso) {
        matrizAdjacencia[origem][destino] = peso;
        matrizAdjacencia[destino][origem] = peso;
    }

    public float getPeso(int origem, int destino) {
        return matrizAdjacencia[origem][destino];
    }

    public int getNumVertices() {
        return numVertices;
    }
}
