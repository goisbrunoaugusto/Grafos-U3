package com.ufrn;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class HeuVizinhoProximo {

    public static class Grafo {
        private int numVertices = 0;
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
    
    public Grafo popularGrafoExcel(String caminhoArquivo) throws IOException {
        InputStream is = getClass().getResourceAsStream(caminhoArquivo);

        if (is == null) {
            throw new IOException("Arquivo não encontrado no resources: " + caminhoArquivo);
        }

        try (InputStream in = is; Workbook workbook = WorkbookFactory.create(in)) {
            Sheet sheet = workbook.getSheetAt(0);
            int numVertices = sheet.getLastRowNum(); 
            Grafo grafo = new Grafo(numVertices);

            for (int i = 1; i <= numVertices; i++) {
                for (int j = 1; j <= numVertices; j++) {
                    var cell = sheet.getRow(i).getCell(j);
                    if (cell != null) {
                        float peso = (float) cell.getNumericCellValue();
                        grafo.adicionarAresta(i- 1, j-1, peso);
                        // System.out.println("Adicionada aresta de " + (i-1) + " para " + (j) + " com peso " + peso);
                    }
                }
            }
            System.out.println("Numero de vertices: " + grafo.getNumVertices());
            return grafo;
        }
    }

    public float calcularCustoRota(List<Integer> rota, Grafo grafo) {
        float custoTotal = 0;

        for (int i = 0; i < rota.size() - 1; i++) {
            int origem = rota.get(i);
            int destino = rota.get(i + 1);
            custoTotal += grafo.getPeso(origem, destino);
        }
        return custoTotal;
    }

    public void melhorarRota(List<Integer> rota, Grafo grafo, float custoAtual){
        boolean houveMelhoria = true;

        while(houveMelhoria){
            houveMelhoria = false;

            for (int i = 1; i < rota.size() - 1; i++) {
                for (int j = i + 1; j < rota.size(); j++) {
                    int aux = rota.get(i);
                    rota.set(i, rota.get(j));
                    rota.set(j, aux);

                    float novoCusto = calcularCustoRota(rota, grafo);
                    if (novoCusto < custoAtual) {
                        custoAtual = novoCusto;
                        houveMelhoria = true;
                        System.out.println("Melhoria encontrada! Novo custo: " + custoAtual);
                    } else {
                        aux = rota.get(i);
                        rota.set(i, rota.get(j));
                        rota.set(j, aux);
                    }
                }
            }
        }
        System.out.println("Rota final otimizada com custo: " + custoAtual);
        System.out.print("1-> ");
        for (int vertice : rota) {
            System.out.print((vertice + 1) + "-> ");
        }
    }

    public void executarHeuristica(Grafo grafo){
        boolean[] visitado = new boolean[grafo.getNumVertices()];
        int verticeAtual = 0;
        visitado[verticeAtual] = true;
        List<Integer> sequencia = new ArrayList<>();
        float pesototal = 0;
        System.out.print("Sequencia de vertices: 1");
        
        for (int i = 0; i < grafo.getNumVertices(); i++) {
            int proximoVertice = -1;
            float menorPeso = Float.MAX_VALUE;
            
            for (int j = 0; j < grafo.getNumVertices(); j++) {
                if (!visitado[j]) {
                    float peso = grafo.getPeso(verticeAtual, j);
                    if (peso > 0 && peso < menorPeso) {
                        menorPeso = peso;
                        proximoVertice = j;
                    }
                }
            }
            if (proximoVertice != -1) {
                visitado[proximoVertice] = true;
                verticeAtual = proximoVertice;
                sequencia.add(verticeAtual);
                pesototal += menorPeso;
                System.out.print("-> " + (proximoVertice + 1));
            }
        }
        System.out.println("\nPeso total do caminho inicialmente calculado: " + pesototal);
        
        melhorarRota(sequencia, grafo, pesototal);
    }

    public static void main(String[] args) {
        System.out.println("Iniciando Heurística do Vizinho Mais Próximo");
        String caminhoArquivo = "/planilha.xlsx";
        HeuVizinhoProximo heu = new HeuVizinhoProximo();
        try {
            Grafo grafo = heu.popularGrafoExcel(caminhoArquivo);
            heu.executarHeuristica(grafo);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
