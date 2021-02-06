package com.vthmgnpipola.matrixcalc.comandos;

import com.vthmgnpipola.matrixcalc.calc.Matriz;
import com.vthmgnpipola.matrixcalc.calc.RegistroMatrizes;

@ComandoRegistrado("rmat")
public class ComandoRemoverMatriz implements Comando {
    @Override
    public boolean checarArgumentos(String args) {
        return args.matches("[a-zA-Z]+(\s+[a-zA-Z]+)*");
    }

    @Override
    public void executar(String args) {
        String[] matrizes = args.split("\s*");
        int matrizesRemovidas = 0;
        for (String matriz : matrizes) {
            Matriz removida = RegistroMatrizes.removerMatriz(matriz);
            if (removida != null) {
                matrizesRemovidas++;
            }
        }

        if (matrizesRemovidas == 1) {
            System.out.println("1 matriz removida.");
        } else {
            System.out.println(matrizesRemovidas + " matrizes removidas.");
        }

        System.out.println();
    }

    @Override
    public String getDescricao() {
        return null;
    }
}
