package com.vthmgnpipola.matrixcalc.comandos;

import com.vthmgnpipola.matrixcalc.calc.Matriz;
import com.vthmgnpipola.matrixcalc.calc.RegistroMatrizes;
import java.util.List;
import java.util.StringJoiner;

@ComandoRegistrado("tipos")
public class ComandoTipos implements Comando {
    @Override
    public boolean checarArgumentos(String args) {
        return args.matches("[a-zA-Z]+(\s+[a-zA-Z]+)*");
    }

    @Override
    public void executar(String args) throws ComandoException {
        String[] nomesMatrizes = args.split("\s+");

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

            System.out.println(ANSI_BOLD + ANSI_YELLOW + "Tipos da matriz " + nomeMatriz + ":" + ANSI_RESET);
            System.out.println(tipos.toString());
            System.out.println();
        }
    }

    @Override
    public String getDescricao() {
        return null;
    }
}
