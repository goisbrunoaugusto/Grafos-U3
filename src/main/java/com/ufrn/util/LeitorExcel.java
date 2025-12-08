package com.ufrn.util;

import com.ufrn.model.Grafo;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import java.io.IOException;
import java.io.InputStream;

public class LeitorExcel {
    public Grafo popularGrafo(String caminho, int aba) throws IOException {
        InputStream is = getClass().getResourceAsStream(caminho);

        if (is == null) {
            throw new IOException("Arquivo não encontrado em " + caminho);
        }

        try (InputStream in = is; Workbook workbook = WorkbookFactory.create(in)) {
            Sheet sheet = workbook.getSheetAt(aba);
            int numVertices = sheet.getLastRowNum();
            Grafo grafo = new Grafo(numVertices);

            for (int i = 1; i <= numVertices; i++) {
                for (int j = 1; j <= numVertices; j++) {
                    var cell = sheet.getRow(i).getCell(j);
                    if (cell != null) {
                        float peso = (float) cell.getNumericCellValue();
                        grafo.adicionarAresta(i - 1, j - 1, peso);
                    }
                }
            }

            return grafo;
        }
    }
}
