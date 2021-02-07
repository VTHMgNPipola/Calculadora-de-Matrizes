package com.vthmgnpipola.matrixcalc.comandos;

import com.vthmgnpipola.matrixcalc.calc.AdicionarSubtrairMatriz;
import com.vthmgnpipola.matrixcalc.calc.AdicionarSubtrairMatrizOptions;
import com.vthmgnpipola.matrixcalc.calc.Matriz;
import com.vthmgnpipola.matrixcalc.calc.RegistroMatrizes;
import java.util.ArrayList;
import java.util.List;

public class ComandoAdicionarSubtrairUtils {
    public void executar(String args, boolean subtrair) throws ComandoException {
        // Adquire as matrizes e o nome da matriz resultante, caso exista
        String[] partes = args.split("\s+");
        List<Matriz> matrizes = new ArrayList<>();
        String nomeMatrizResultante = null;
        for (int i = 0; i < partes.length; i++) {
            if (partes[i].equals("-para")) {
                nomeMatrizResultante = partes[i + 1];
                break;
            }

            matrizes.add(RegistroMatrizes.getMatriz(partes[i]));
        }

        // Realiza a operação
        Matriz[] matrizesArray = new Matriz[matrizes.size()];
        matrizesArray = matrizes.toArray(matrizesArray);

        AdicionarSubtrairMatrizOptions options = new AdicionarSubtrairMatrizOptions();
        options.subtrair = subtrair;
        Matriz resultado = new AdicionarSubtrairMatriz().calcular(matrizesArray, options);

        // Registra a matriz, caso necessário
        if (nomeMatrizResultante != null) {
            RegistroMatrizes.registrarMatriz(nomeMatrizResultante, resultado);
        } else {
            nomeMatrizResultante = "Resultado";
        }

        System.out.println(RegistroMatrizes.getMatrizString(nomeMatrizResultante, resultado));
    }
}
