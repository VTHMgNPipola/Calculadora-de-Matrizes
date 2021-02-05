package com.vthmgnpipola.matrixcalc.calc;

import java.util.HashMap;
import java.util.Map;

public class RegistroMatrizes {
    private static final Map<String, Matriz> matrizes = new HashMap<>();

    public static void adicionarMatriz(String nome, Matriz matriz) {
        matrizes.put(nome, matriz);
    }

    public static Matriz removerMatriz(String nome) {
        return matrizes.remove(nome);
    }

    public static Matriz getMatriz(String nome) {
        return matrizes.get(nome);
    }

    public static int getQuantidadeMatrizes() {
        return matrizes.size();
    }
}
