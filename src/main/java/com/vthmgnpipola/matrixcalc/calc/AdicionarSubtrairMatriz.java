package com.vthmgnpipola.matrixcalc.calc;

public class AdicionarSubtrairMatriz implements CalculadoraMatrizStrategy<Matriz, AdicionarSubtrairMatrizOptions> {
    @Override
    public Matriz calcular(Matriz[] matrizes, AdicionarSubtrairMatrizOptions opts) {
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
                    double valorFinal = resultado.get(x, y);
                    double valorMatriz = matriz.get(x, y);
                    if (opts.subtrair) {
                        valorFinal -= valorMatriz;
                    } else {
                        valorFinal += valorMatriz;
                    }

                    resultado.set(x, y, valorFinal);
                }
            }
        }
        return resultado;
    }
}
