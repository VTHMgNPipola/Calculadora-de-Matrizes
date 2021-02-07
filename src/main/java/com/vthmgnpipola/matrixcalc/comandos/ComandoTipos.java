package com.vthmgnpipola.matrixcalc.comandos;

import com.vthmgnpipola.matrixcalc.calc.Matriz;
import com.vthmgnpipola.matrixcalc.calc.RegistroMatrizes;
import java.util.List;
import java.util.Set;
import java.util.StringJoiner;

@ComandoRegistrado("tipos")
public class ComandoTipos implements Comando {
    @Override
    public boolean checarArgumentos(String args) {
        return args.isEmpty() || args.matches("[a-zA-Z]+(\s+[a-zA-Z]+)*");
    }

    @Override
    public void executar(String args) throws ComandoException {
        String[] nomesMatrizes;
        if (args.isEmpty()) {
            Set<String> nomesSet = RegistroMatrizes.getMatrizes().keySet();
            nomesMatrizes = new String[nomesSet.size()];
            nomesMatrizes = nomesSet.toArray(nomesMatrizes);
        } else {
            nomesMatrizes = args.split("\s+");
        }

        for (String nomeMatriz : nomesMatrizes) {
            Matriz matriz = RegistroMatrizes.getMatriz(nomeMatriz);
            if (matriz == null) {
                throw new ComandoException("A matriz '" + nomeMatriz + "' não existe!");
            }

            // Calcula os tipos
            StringJoiner tipos = new StringJoiner(", ");
            List<Matriz.TipoMatriz> tiposList = matriz.getTipos();
            if (!tiposList.isEmpty()) {
                tiposList.forEach(t -> tipos.add(t.toString()));
            } else {
                tipos.add("Nenhum");
            }

            System.out.println(ANSI_YELLOW + "Tipos da matriz " + ANSI_BOLD + nomeMatriz + ANSI_RESET + ":");
            System.out.println(tipos.toString());
            System.out.println();
        }
    }

    @Override
    public String getDescricao() {
        return """
               Calcula e exibe o tipo das matrizes especificadas, ou de todas as matrizes se nenhuma for especificada.
               Para especificar matrizes para serem analisadas, insira os nomes da matrizes como argumentos a este
               comando, separados por espaços.""";
    }
}
