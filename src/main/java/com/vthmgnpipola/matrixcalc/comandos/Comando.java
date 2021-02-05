package com.vthmgnpipola.matrixcalc.comandos;

public interface Comando {
    void executar(String[] args);

    String getDescricao();
}
