package com.vthmgnpipola.matrixcalc.calc;

import java.util.ArrayList;
import java.util.List;

public class Matriz {
    private final double[][] dados;

    public enum TipoMatriz {
        QUADRADA("Quadrada"), IDENTIDADE("Identidade"), DIAGONAL("Diagonal"),
        TRIANGULO_L("Triangular inferior L"), TRIANGULO_U("Triangular superior U"),
        UNITARIA("Unitária"), NULA("Nula");

        private final String nome;

        TipoMatriz(String nome) {
            this.nome = nome;
        }


        @Override
        public String toString() {
            return nome;
        }
    }

    public Matriz(double[][] dados) {
        this.dados = dados;
    }

    /**
     * Cria uma matriz, copiando uma original. Ao realizar uma operação com uma matriz, a menos que esse seja exatamente
     * o desejado, é necessário utilizar este construtor, já que ao passar somente uma referência, qualquer operação na
     * matriz irá alterar seus dados.
     *
     * @param original Matriz que será copiada.
     */
    public Matriz(Matriz original) {
        dados = new double[original.linhas()][];
        for (int y = 0; y < original.linhas(); y++) {
            dados[y] = new double[original.colunas()];
            System.arraycopy(original.dados[y], 0, dados[y], 0, original.colunas());
        }
    }

    public double get(int x, int y) {
        return dados[y][x];
    }

    public void set(int x, int y, double valor) {
        dados[y][x] = valor;
    }

    public int linhas() {
        return dados.length;
    }

    public int colunas() {
        return dados[0].length;
    }

    public List<TipoMatriz> getTipos() {
        List<TipoMatriz> tipos = new ArrayList<>();

        if (linhas() == 1 && colunas() == 1) {
            tipos.add(TipoMatriz.UNITARIA);
        } if (linhas() == colunas()) {
            tipos.add(TipoMatriz.QUADRADA);
        }

        if (tipos.contains(TipoMatriz.QUADRADA)) {
            boolean identidade = true;
            boolean trianguloL = true, trianguloU = true;
            boolean nula = true;
            for (int y = 0; y < linhas(); y++) {
                for (int x = 0; x < colunas(); x++) {
                    final double valor = get(x, y);

                    // Matriz identidade
                    if (y == x && valor != 1) {
                        identidade = false;
                    } else if (y != x && valor != 0) {
                        identidade = false;
                    }

                    // Matriz triangula
                    if (y < x && valor != 0) {
                        trianguloL = false;
                    } else if (y > x && valor != 0) {
                        trianguloU = false;
                    }

                    // Matriz nula
                    if (valor != 0) {
                        nula = false;
                    }
                }
            }

            if (identidade) {
                tipos.add(TipoMatriz.IDENTIDADE);
            } if (trianguloL) {
                tipos.add(TipoMatriz.TRIANGULO_L);
            } if (trianguloU) {
                tipos.add(TipoMatriz.TRIANGULO_U);
            } if (trianguloL && trianguloU) {
                tipos.add(TipoMatriz.DIAGONAL);
            } if (nula) {
                tipos.add(TipoMatriz.NULA);
            }
        }

        return tipos;
    }
}
