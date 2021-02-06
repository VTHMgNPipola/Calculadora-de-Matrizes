package com.vthmgnpipola.matrixcalc.comandos;

import com.vthmgnpipola.matrixcalc.calc.Matriz;
import com.vthmgnpipola.matrixcalc.calc.RegistroMatrizes;
import java.util.List;
import java.util.regex.MatchResult;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@ComandoRegistrado("regmat")
public class ComandoRegistrarMatriz implements Comando {
    private static final String REGEX_NUMERO = "-?\\d+(\\.\\d+)?";
    private static final Pattern PATTERN_NUMERO = Pattern.compile(REGEX_NUMERO);

    @Override
    public boolean checarArgumentos(String args) {
        return args.matches(String.format("[a-zA-Z]+\s*=\s*(\\{(\s*%s\s*,\s*)*(\s*%s\s*)+}\s*;\s*)+", REGEX_NUMERO,
                REGEX_NUMERO));
    }

    @Override
    public void executar(String args) throws ComandoException {
        String[] partes = args.split("\s*=\s*");
        String nome = partes[0];

        String matrizStr = partes[1];
        int quantidadeColunas = -1;
        String[] linhasStr = matrizStr.split("\s*;\s*");
        double[][] linhas = new double[linhasStr.length][];
        for (int linhaIndex = 0; linhaIndex < linhasStr.length; linhaIndex++) {
            List<MatchResult> matchResults =
                    PATTERN_NUMERO.matcher(linhasStr[linhaIndex]).results().collect(Collectors.toList());

            if (quantidadeColunas == -1) {
                quantidadeColunas = matchResults.size();
            } else if (quantidadeColunas != matchResults.size()) {
                throw new ComandoException("As quantidades de colunas entre as linhas não são iguais!");
            }

            double[] linha = new double[matchResults.size()];
            for (int colunaIndex = 0; colunaIndex < matchResults.size(); colunaIndex++) {
                linha[colunaIndex] = Double.parseDouble(matchResults.get(colunaIndex).group());
            }
            linhas[linhaIndex] = linha;
        }

        Matriz matriz = new Matriz(linhas);
        RegistroMatrizes.adicionarMatriz(nome, matriz);
    }

    @Override
    public String getDescricao() {
        return null;
    }
}
