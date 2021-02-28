package com.vthmgnpipola.matrixcalc.calc;

import com.vthmgnpipola.matrixcalc.comandos.ComandoException;
import com.vthmgnpipola.matrixcalc.funcoes.RegistroFuncoes;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;
import org.ejml.data.DMatrixRMaj;
import org.ejml.equation.Equation;

public class MatrizHelper {
    private static final Map<String, DMatrixRMaj> matrizes = new HashMap<>();
    private static final Map<String, Double> escalares = new HashMap<>();

    public static final int MATRIZ_LATEX_SIMPLES = 0;
    public static final int MATRIZ_LATEX_PARENTESES = 1;
    public static final int MATRIZ_LATEX_COLCHETES = 2;
    public static final int MATRIZ_LATEX_CHAVES = 3;
    public static final int MATRIZ_LATEX_PIPES = 4;
    public static final int MATRIZ_LATEX_DOUBLE_PIPES = 5;

    public static void registrar(String nome, DMatrixRMaj matriz) throws ComandoException {
        if (escalares.containsKey(nome)) {
            throw new ComandoException("Já existe um escalar com o nome '" + nome + "'!");
        }

        matrizes.put(nome, matriz);
    }

    public static void registrar(String nome, double escalar) throws ComandoException {
        if (matrizes.containsKey(nome)) {
            throw new ComandoException("Já existe uma matriz com o nome '" + nome + "'!");
        }

        escalares.put(nome, escalar);
    }

    public static void removerMatriz(String nome) {
        matrizes.remove(nome);
    }

    public static void removerEscalar(String nome) {
        escalares.remove(nome);
    }

    public static Map<String, DMatrixRMaj> getMatrizes() {
        return new HashMap<>(matrizes);
    }

    public static Map<String, Double> getEscalares() {
        return new HashMap<>(escalares);
    }

    public static Equation criarEquacao() {
        Equation equation = new Equation();
        matrizes.forEach((nome, matriz) -> equation.alias(matriz, nome));
        escalares.forEach((nome, escalar) -> equation.alias(escalar, nome));
        RegistroFuncoes.incluirFuncoes(equation.getFunctions());
        return equation;
    }

    public static String lookupEscalarString(String nome) {
        return getEscalarString(nome, escalares.get(nome));
    }

    public static String getEscalarString(String nome, double escalar) {
        return String.format("%s = %s\n", nome, escalar);
    }

    public static String lookupMatrizString(String nome) throws ComandoException {
        return getMatrizString(nome, matrizes.get(nome));
    }

    public static String getMatrizString(String nome, DMatrixRMaj matriz) throws ComandoException {
        if (matriz == null) {
            throw new ComandoException("A matriz '" + nome + "' não existe!");
        }

        // Descobre o tamanho do maior valor
        int[] tamanhoValores = new int[matriz.numCols];
        for (int x = 0; x < matriz.numCols; x++) {
            for (int y = 0; y < matriz.numRows; y++) {
                String valorStr = matriz.get(y, x) + "";
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
                stringJoiner.add(String.format("%1$" + tamanhoValores[x] + "s", matriz.get(y, x)));
            }
            sb.append(stringJoiner.toString()).append("]\n");
        }

        return sb.toString();
    }

    public static String getMatrizLatexString(String nome, DMatrixRMaj matriz, int estilo, boolean exibirNome)
            throws ComandoException {
        StringBuilder sb = new StringBuilder();

        if (exibirNome) {
            sb.append(nome).append(" = ");
        }

        String estiloString;
        switch (estilo) {
            case MATRIZ_LATEX_SIMPLES -> estiloString = "matrix";
            case MATRIZ_LATEX_PARENTESES -> estiloString = "pmatrix";
            case MATRIZ_LATEX_COLCHETES -> estiloString = "bmatrix";
            case MATRIZ_LATEX_CHAVES -> estiloString = "Bmatrix";
            case MATRIZ_LATEX_PIPES -> estiloString = "vmatrix";
            case MATRIZ_LATEX_DOUBLE_PIPES -> estiloString = "Vmatrix";
            default -> throw new ComandoException("Modo de renderização da matriz em LaTeX inválido!");
        }

        sb.append("\\begin{").append(estiloString).append("}\n");
        for (int y = 0; y < matriz.numRows; y++) {
            StringJoiner stringJoiner = new StringJoiner(" & ");
            for (int x = 0; x < matriz.numCols; x++) {
                stringJoiner.add(String.valueOf(matriz.get(y, x)));
            }

            sb.append(stringJoiner.toString());
            if (y < matriz.numRows - 1) { // Se ainda não for a última linha
                sb.append("\\\\"); // Insere os caracteres '\\'
            }
            sb.append("\n");
        }
        sb.append("\\end{").append(estiloString).append("}\n");

        return sb.toString();
    }

    public static List<TipoMatriz> getTipos(DMatrixRMaj matriz) {
        List<TipoMatriz> tipos = new ArrayList<>();

        if (matriz.numRows == 1 && matriz.numCols == 1) {
            tipos.add(TipoMatriz.UNITARIA);
        } if (matriz.numRows == matriz.numCols) {
            tipos.add(TipoMatriz.QUADRADA);
        }

        if (tipos.contains(TipoMatriz.QUADRADA)) {
            boolean identidade = true;
            boolean trianguloL = true, trianguloU = true;
            boolean nula = true;
            for (int y = 0; y < matriz.numRows; y++) {
                for (int x = 0; x < matriz.numCols; x++) {
                    final double valor = matriz.get(y, x);

                    // Matriz identidade
                    if (y == x && valor != 1) {
                        identidade = false;
                    } else if (y != x && valor != 0) {
                        identidade = false;
                    }

                    // Matriz triangula
                    if (y < x && valor != 0) {
                        trianguloL = false;
                    } else if (y > x && valor != 0) {
                        trianguloU = false;
                    }

                    // Matriz nula
                    if (valor != 0) {
                        nula = false;
                    }
                }
            }

            if (identidade) {
                tipos.add(TipoMatriz.IDENTIDADE);
            } if (trianguloL) {
                tipos.add(TipoMatriz.TRIANGULO_L);
            } if (trianguloU) {
                tipos.add(TipoMatriz.TRIANGULO_U);
            } if (trianguloL && trianguloU) {
                tipos.add(TipoMatriz.DIAGONAL);
            } if (nula) {
                tipos.add(TipoMatriz.NULA);
            }
        }

        return tipos;
    }
}
