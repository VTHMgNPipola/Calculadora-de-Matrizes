package com.vthmgnpipola.matrixcalc.calc;

public class AdicionarMatriz implements CalculadoraMatrizStrategy<Matriz> {
    @Override
    public Matriz calcular(Matriz[] matrizes) {
        if (matrizes.length < 2) {
            throw new CalculadoraException("É necessário haver pelo menos 2 matrizes para serem adicionadas!");
        }

        Matriz resultado = new Matriz(matrizes[0]);
        for (int i = 1; i < matrizes.length; i++) {
            Matriz matriz = matrizes[i];

            if (matriz == null) {
                throw new CalculadoraException("Uma matriz não existente não pode ser adicionada!");
            } else if (matriz.linhas() != resultado.linhas() || matriz.colunas() != resultado.colunas()) {
                throw new CalculadoraException("Matrizes com tamanhos diferentes não podem ser adicionadas!");
            }

            for (int y = 0; y < matriz.linhas(); y++) {
                for (int x = 0; x < matriz.colunas(); x++) {
                    resultado.set(x, y, resultado.get(x, y) + matriz.get(x, y));
                }
            }
        }
        return resultado;
    }
}
