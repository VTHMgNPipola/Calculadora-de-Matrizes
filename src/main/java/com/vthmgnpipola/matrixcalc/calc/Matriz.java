package com.vthmgnpipola.matrixcalc.calc;

import java.util.ArrayList;
import java.util.List;

public class Matriz {
    private final double[][] dados;

    public enum TipoMatriz {
        QUADRADA("Quadrada"), IDENTIDADE("Identidade"), DIAGONAL("Diagonal"),
        TRIANGULO_L("Triangular inferior L"), TRIANGULO_U("Triangular superior U");

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

        if (linhas() == colunas()) {
            tipos.add(TipoMatriz.QUADRADA);
        }

        if (tipos.contains(TipoMatriz.QUADRADA)) {
            boolean identidade = true;
            boolean trianguloL = true, trianguloU = true;
            for (int y = 0; y < linhas(); y++) {
                for (int x = 0; x < colunas(); x++) {
                    final double valor = get(x, y);
                    if (y == x && valor != 1) {
                        identidade = false;
                    } else if (y != x && valor != 0) {
                        identidade = false;
                    }

                    if (y < x && valor != 0) {
                        trianguloL = false;
                    } else if (y > x && valor != 0) {
                        trianguloU = false;
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
            }
        }

        return tipos;
    }
}
