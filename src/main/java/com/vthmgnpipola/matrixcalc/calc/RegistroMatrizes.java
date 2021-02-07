package com.vthmgnpipola.matrixcalc.calc;

import com.vthmgnpipola.matrixcalc.comandos.ComandoException;
import java.util.HashMap;
import java.util.Map;
import java.util.StringJoiner;

public class RegistroMatrizes {
    private static final Map<String, Matriz> matrizes = new HashMap<>();

    public static void registrarMatriz(String nome, Matriz matriz) {
        matrizes.put(nome, matriz);
    }

    public static Matriz removerMatriz(String nome) {
        return matrizes.remove(nome);
    }

    public static Matriz getMatriz(String nome) {
        return matrizes.get(nome);
    }

    public static Map<String, Matriz> getMatrizes() {
        return new HashMap<>(matrizes);
    }

    public static int getQuantidadeMatrizes() {
        return matrizes.size();
    }

    public static String getMatrizString(String nome) throws ComandoException {
        return getMatrizString(nome, getMatriz(nome));
    }

    public static String getMatrizString(String nome, Matriz matriz) throws ComandoException {
        if (matriz == null) {
            throw new ComandoException("A matriz '" + nome + "' não existe!");
        }

        // Descobre o tamanho do maior valor
        int[] tamanhoValores = new int[matriz.colunas()];
        for (int x = 0; x < matriz.colunas(); x++) {
            for (int y = 0; y < matriz.linhas(); y++) {
                String valorStr = matriz.get(x, y) + "";
                if (valorStr.length() > tamanhoValores[x]) {
                    tamanhoValores[x] = valorStr.length();
                }
            }
        }

        // Inicialização do buffer com o nome da matriz e o símbolo '='
        StringBuilder sb = new StringBuilder();
        sb.append(nome).append(" = ");

        // Inicialização dos parâmetros para adicionar espaço antes dos colchetes
        int paddingColchete = sb.length();
        String abreColchete = "[";

        for (int y = 0; y < matriz.linhas(); y++) {
            sb.append(abreColchete);
            abreColchete = " ".repeat(paddingColchete) + "[";

            // Cria a linha
            StringJoiner stringJoiner = new StringJoiner(", ");
            for (int x = 0; x < matriz.colunas(); x++) {
                stringJoiner.add(String.format("%1$" + tamanhoValores[x] + "s", matriz.get(x, y)));
            }
            sb.append(stringJoiner.toString()).append("]\n");
        }

        return sb.toString();
    }
}
