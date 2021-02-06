package com.vthmgnpipola.matrixcalc.comandos;

import com.vthmgnpipola.matrixcalc.calc.RegistroMatrizes;
import java.util.Set;

@ComandoRegistrado("listar")
public class ComandoListar implements Comando {
    @Override
    public boolean checarArgumentos(String args) {
        return args.isEmpty() || args.matches("[a-zA-Z]+(\s*[a-zA-Z]+)*");
    }

    @Override
    public void executar(String args) throws ComandoException {
        String[] nomesMatrizes;
        if (args.isEmpty()) {
            Set<String> setMatrizes = RegistroMatrizes.getMatrizes().keySet();
            nomesMatrizes = new String[setMatrizes.size()];
            nomesMatrizes = setMatrizes.toArray(nomesMatrizes);

        } else {
            nomesMatrizes = args.split("\s*");
        }

        for (String nome : nomesMatrizes) {
            System.out.println(RegistroMatrizes.getMatrizString(nome));
        }

        if (nomesMatrizes.length == 1) {
            System.out.println("1 matriz listada");
        } else {
            System.out.println(nomesMatrizes.length + " matrizes listadas");
        }
    }

    @Override
    public String getDescricao() {
        return null;
    }
}
