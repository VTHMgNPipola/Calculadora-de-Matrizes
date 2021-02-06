package com.vthmgnpipola.matrixcalc.comandos;

import com.vthmgnpipola.matrixcalc.MatrixCalcApplication;

@ComandoRegistrado("sair")
public class ComandoSair implements Comando {
    @Override
    public boolean checarArgumentos(String args) {
        return false;
    }

    @Override
    public void executar(String args) {
        MatrixCalcApplication.sair();
    }

    @Override
    public String getDescricao() {
        return "Finaliza a execução da calculadora de matrizes.";
    }
}
