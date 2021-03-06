package com.vthmgnpipola.matrixcalc.comandos;

import com.vthmgnpipola.matrixcalc.calc.MatrizHelper;
import java.util.List;
import java.util.regex.MatchResult;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import org.ejml.data.DMatrixRMaj;

@ComandoRegistrado("registrar")
public class ComandoRegistrar implements Comando {
    private static final String REGEX_NUMERO = "-?\\d+(\\.\\d+)?";
    private static final Pattern PATTERN_NUMERO = Pattern.compile(REGEX_NUMERO);

    private static final Pattern PATTERN_MATRIZ = Pattern.compile(
            String.format("[a-zA-Z]+\s*=\s*(\\{(\s*%s\s*,\s*)*(\s*%s\s*)+}\s*;\s*)+", REGEX_NUMERO, REGEX_NUMERO));
    private static final Pattern PATTERN_ESCALAR = Pattern.compile(String.format("[a-zA-Z]+\s*=\s*%s", REGEX_NUMERO));

    @Override
    public boolean checarArgumentos(String args) {
        return PATTERN_MATRIZ.matcher(args).matches() || PATTERN_ESCALAR.matcher(args).matches();
    }

    @Override
    public void executar(String args) throws ComandoException {
        String[] partes = args.split("\s*=\s*");
        String nome = partes[0];

        if (PATTERN_MATRIZ.matcher(args).matches()) {
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

            DMatrixRMaj matriz = new DMatrixRMaj(linhas);
            MatrizHelper.registrar(nome, matriz);
        } else if (PATTERN_ESCALAR.matcher(args).matches()) {
            String numeroStr = partes[1];
            double numero = Double.parseDouble(PATTERN_NUMERO.matcher(numeroStr).group());

            MatrizHelper.registrar(nome, numero);
        }
    }

    @Override
    public String getDescricao() {
        return """
               Adiciona uma matriz ou escalar, que poderá então ser utilizada em cálculos.
               A sintaxe para adicionar um escalar é:
                * o nome do escalar (qualquer combinação de letrar latinas maiúsculas ou minúsculas)
                * o símbolo '='
                * o valor do escalar, onde o delimitador decimal é o ponto '.'
               A sintaxe para adicionar uma matriz é:
                * o nome da matriz (qualquer combinação de letras latinas maiúsculas ou minúsculas)
                * o símbolo '='
                * os dados da matriz
               Os dados da matriz são representados da seguinte forma:
                * o símbolo '{'
                * os números da linha, onde o delimitador decimal é o ponto '.', e o delimitador entre valores é a
                  vírgula ','
                * o símbolo '}'
                * o símbolo ';'
               Note que uma quantidade arbitrária de caracteres de espaçamento (espaços, tabs, etc) pode estar entre
               qualquer elemento apresentado acima.""";
    }
}
