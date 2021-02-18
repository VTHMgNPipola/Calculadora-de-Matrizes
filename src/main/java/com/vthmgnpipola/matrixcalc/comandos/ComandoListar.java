package com.vthmgnpipola.matrixcalc.comandos;

import com.vthmgnpipola.matrixcalc.calc.MatrizHelper;
import java.util.ArrayList;
import java.util.List;

@ComandoRegistrado(value = "listar", exportavel = false)
public class ComandoListar implements Comando {
    @Override
    public boolean checarArgumentos(String args) {
        return args.isEmpty() || args.matches("[a-zA-Z]+(\s*[a-zA-Z]+)*");
    }

    @Override
    public void executar(String args) throws ComandoException {
        String[] nomes;
        if (args.isEmpty()) {
            List<String> nomesList = new ArrayList<>(MatrizHelper.getMatrizes().keySet());
            nomesList.addAll(MatrizHelper.getEscalares().keySet());
            nomes = new String[nomesList.size()];
            nomes = nomesList.toArray(nomes);
        } else {
            nomes = args.split("\s*");
        }

        int matrizesListadas = 0;
        int escalaresListados = 0;
        for (String nome : nomes) {
            if (MatrizHelper.getMatrizes().containsKey(nome)) {
                System.out.println(MatrizHelper.lookupMatrizString(nome));
                matrizesListadas++;
            } else if (MatrizHelper.getEscalares().containsKey(nome)) {
                System.out.println(MatrizHelper.lookupEscalarString(nome));
                escalaresListados++;
            }
        }

        if (matrizesListadas == 1) {
            System.out.println("1 matriz listada.");
        } else {
            System.out.println(matrizesListadas + " matrizes listadas.");
        }

        if (escalaresListados == 1) {
            System.out.println("1 escalar listado.");
        } else {
            System.out.println(escalaresListados + " escalares listados.");
        }
    }

    @Override
    public String getDescricao() {
        return """
               Lista matrizes na memória da calculadora de matrizes.
               Para listar todas as matrizes, só é necessário chamar o comando sem qualquer argumentos. Para listar
               matrizes específicas, coloque o nome das matrizes que serão listadas, separados por espaço.""";
    }
}
