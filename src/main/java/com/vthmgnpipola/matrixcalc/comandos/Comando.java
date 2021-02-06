package com.vthmgnpipola.matrixcalc.comandos;

public interface Comando {
    boolean checarArgumentos(String args);

    void executar(String args) throws ComandoException;

    String getDescricao();
}
