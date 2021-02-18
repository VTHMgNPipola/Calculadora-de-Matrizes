package com.vthmgnpipola.matrixcalc.comandos;

import com.vthmgnpipola.matrixcalc.calc.MatrizHelper;

@ComandoRegistrado("remover")
public class ComandoRemover implements Comando {
    @Override
    public boolean checarArgumentos(String args) {
        return args.matches("[a-zA-Z]+(\s+[a-zA-Z]+)*");
    }

    @Override
    public void executar(String args) {
        String[] partes = args.split("\s*");
        int matrizesRemovidas = 0;
        int escalaresRemovidos = 0;
        for (String parte : partes) {
            if (MatrizHelper.getMatrizes().containsKey(parte)) {
                MatrizHelper.getMatrizes().remove(parte);
                matrizesRemovidas++;
            } else if (MatrizHelper.getEscalares().containsKey(parte)) {
                MatrizHelper.getEscalares().remove(parte);
                escalaresRemovidos++;
            }
        }

        if (matrizesRemovidas == 1) {
            System.out.println("1 matriz removida.");
        } else {
            System.out.println(matrizesRemovidas + " matrizes removidas.");
        }

        if (escalaresRemovidos == 1) {
            System.out.println("1 escalar removido.");
        } else {
            System.out.println(matrizesRemovidas + " escalares removidos.");
        }

        System.out.println();
    }

    @Override
    public String getDescricao() {
        return """
               Remove matrizes da memória da calculadora de matrizes.
               Para remover uma ou mais matrizes, insira como argumentos ao comando o nome das matrizes, separado por
               espaço""";
    }
}
