package com.vthmgnpipola.matrixcalc.comandos;

import com.vthmgnpipola.matrixcalc.calc.AdicionarMatriz;
import com.vthmgnpipola.matrixcalc.calc.Matriz;
import com.vthmgnpipola.matrixcalc.calc.RegistroMatrizes;
import java.util.ArrayList;
import java.util.List;

@ComandoRegistrado("adicionar")
public class ComandoAdicionar implements Comando {
    @Override
    public boolean checarArgumentos(String args) {
        return args.matches("[a-zA-Z]+(\s+[a-zA-Z]+)+(\s+-para\s+[a-zA-Z]+)?");
    }

    @Override
    public void executar(String args) throws ComandoException {
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

        // Realiza a adição
        Matriz[] matrizesArray = new Matriz[matrizes.size()];
        matrizesArray = matrizes.toArray(matrizesArray);
        Matriz resultado = new AdicionarMatriz().calcular(matrizesArray);

        // Registra a matriz, caso necessário
        if (nomeMatrizResultante != null) {
            RegistroMatrizes.registrarMatriz(nomeMatrizResultante, resultado);
        } else {
            nomeMatrizResultante = "Resultado";
        }

        System.out.println(RegistroMatrizes.getMatrizString(nomeMatrizResultante, resultado));
    }

    @Override
    public String getDescricao() {
        return """
               Adiciona matrizes, e retorna o resultado ou o armazena em um matriz. Para selecionar as matrizes a serem
               adicionadas, só é necessário listar os seus nome como argumentos, separado por espaços.
               Para indicar uma matriz, onde o resultado será armazenado, utilize '-para <nome-da-matriz>' após a lista
               das matrizes a serem adicionadas.""";
    }
}
