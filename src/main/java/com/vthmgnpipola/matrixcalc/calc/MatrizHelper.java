package com.vthmgnpipola.matrixcalc.calc;

import com.vthmgnpipola.matrixcalc.comandos.ComandoException;
import java.util.StringJoiner;
import org.ejml.data.DMatrixRMaj;
import org.ejml.equation.Equation;

public class MatrizHelper {
    private static final Equation EQUATION = new Equation();

    public static Equation getEquation() {
        return EQUATION;
    }

    public static String lookupMatrizString(String nome) throws ComandoException {
        return getMatrizString(nome, EQUATION.lookupDDRM(nome));
    }

    public static String getMatrizString(String nome, DMatrixRMaj matriz) throws ComandoException {
        if (matriz == null) {
            throw new ComandoException("A matriz '" + nome + "' não existe!");
        }

        // Descobre o tamanho do maior valor
        int[] tamanhoValores = new int[matriz.numCols];
        for (int x = 0; x < matriz.numCols; x++) {
            for (int y = 0; y < matriz.numRows; y++) {
                String valorStr = matriz.get(x, y) + "";
                if (valorStr.length() > tamanhoValores[x]) {
                    tamanhoValores[x] = valorStr.length();
                }
            }
        }

        // Inicialização do buffer com o nome da matriz e o símbolo '='
        StringBuilder sb = new StringBuilder();
        sb.append(nome).append(" = ");

        // Inicialização dos parâmetros para adicionar espaço antes dos colchetes
        int paddingColchete = sb.length();
        String abreColchete = "[";

        for (int y = 0; y < matriz.numRows; y++) {
            sb.append(abreColchete);
            abreColchete = " ".repeat(paddingColchete) + "[";

            // Cria a linha
            StringJoiner stringJoiner = new StringJoiner(", ");
            for (int x = 0; x < matriz.numCols; x++) {
                stringJoiner.add(String.format("%1$" + tamanhoValores[x] + "s", matriz.get(x, y)));
            }
            sb.append(stringJoiner.toString()).append("]\n");
        }

        return sb.toString();
    }

    public static String getMatrizLatexString(String nome, DMatrixRMaj matriz) {
        return null;
    }
}
