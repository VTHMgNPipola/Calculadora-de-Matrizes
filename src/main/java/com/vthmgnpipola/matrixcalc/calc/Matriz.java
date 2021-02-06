package com.vthmgnpipola.matrixcalc.calc;

public class Matriz {
    private double[][] dados;

    public Matriz(double[][] dados) {
        this.dados = dados;
    }

    public double get(int x, int y) {
        return dados[y][x];
    }

    public void set(int x, int y, double valor) {
        dados[y][x] = valor;
    }
}
