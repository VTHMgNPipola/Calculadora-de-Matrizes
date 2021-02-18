package com.vthmgnpipola.matrixcalc.calc;

import java.util.HashMap;
import java.util.Map;

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
}
