package com.vthmgnpipola.matrixcalc.comandos;

import com.vthmgnpipola.matrixcalc.calc.MatrizHelper;
import java.util.HashMap;
import java.util.Map;
import org.ejml.data.DMatrixRMaj;

@ComandoRegistrado("latex")
public class ComandoLatex implements Comando {
    @Override
    public boolean checarArgumentos(String args) {
        return true;
    }

    @Override
    public void executar(String args) throws ComandoException {
        final String estilosRegex = "sim|simples|par|parenteses|col|colchetes|cha|chaves|pip|pipes|dpi|double-pipes";

        String[] partes = args.split("\s+");

        boolean exibirNome = true;
        int estilo = MatrizHelper.MATRIZ_LATEX_COLCHETES;
        Map<String, DMatrixRMaj> matrizes = new HashMap<>();
        for (String parte : partes) {
            if (parte.matches("(-en=(verdadeiro|falso))|(-exibirNone=(verdadeiro|falso))")) {
                String modo = parte.split("=")[1];
                exibirNome = modo.equals("verdadeiro");
            } else if (parte.matches(String.format("(-s=(%s))|(-estilo=(%s))", estilosRegex, estilosRegex))) {
                String modo = parte.split("=")[1];
                switch (modo) {
                    case "sim", "simples" -> estilo = MatrizHelper.MATRIZ_LATEX_SIMPLES;
                    case "par", "parenteses" -> estilo = MatrizHelper.MATRIZ_LATEX_PARENTESES;
                    case "col", "colchetes" -> estilo = MatrizHelper.MATRIZ_LATEX_COLCHETES;
                    case "cha", "chaves" -> estilo = MatrizHelper.MATRIZ_LATEX_CHAVES;
                    case "pip", "pipes" -> estilo = MatrizHelper.MATRIZ_LATEX_PIPES;
                    case "dpi", "double-pipes" -> estilo = MatrizHelper.MATRIZ_LATEX_DOUBLE_PIPES;
                }
            } else if (!parte.isBlank()) {
                DMatrixRMaj matriz = MatrizHelper.getMatrizes().get(parte);
                if (matriz != null) {
                    matrizes.put(parte, matriz);
                } else {
                    throw new ComandoException("A matriz '" + parte + "' não existe!");
                }
            }
        }

        if (matrizes.isEmpty()) {
            matrizes = MatrizHelper.getMatrizes();
        }

        for (Map.Entry<String, DMatrixRMaj> entry : matrizes.entrySet()) {
            String latex = MatrizHelper.getMatrizLatexString(entry.getKey(), entry.getValue(), estilo, exibirNome);
            System.out.println(latex);
        }

        System.out.println((matrizes.size() == 1 ? "1 matriz" : matrizes.size() + " matrizes") + " renderizadas.");
    }

    @Override
    public String getDescricao() {
        return """
               Renderiza as matrizes especificadas para LaTeX, ou todas as matrizes, se nenhuma for especificada.
               Dessa maneira, a matriz gerada pode ser inserida em um documento LaTeX muito facilmente, sem ter que
               manualmente copiar a matriz inteira.
               Para especificar uma matriz, é necessário somente inserir o nome dela como argumento para este comando.
               Para especificar se deseja inserir o nome da matriz no resultado (habilitado por padrão), utilize o
               argumento "-en=<modo>" ou "-exibirNome=<modo>", onde "modo" por ser "verdadeiro" ou "falso".
               Para modificar o estilo das matrizes (definido como colchetes por padrão), utilize o argumento
               "-s=<modo>" ou "-estilo=<modo>". Os estilo disponíveis são:
                * 'sim' ou 'simples': Matriz simples, sem nenhuma decoração.
                * 'par' ou 'parenteses': Parênteses são usados na decoração da matriz.
                * 'col' ou 'colchetes': Colchetes são usados na decoração da matriz.
                * 'cha' ou 'chaves': Chaves são usados na decoração da matriz.
                * 'pip' ou 'pipes': Pipes são usados na decoração da matriz.
                * 'dpi' ou 'double-pipes': Double-pipes são usados na decoração da matriz.""";
    }
}
