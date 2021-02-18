package com.vthmgnpipola.matrixcalc.calc;

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
