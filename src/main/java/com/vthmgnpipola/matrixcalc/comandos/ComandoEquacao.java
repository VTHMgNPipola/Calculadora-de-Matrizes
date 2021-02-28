package com.vthmgnpipola.matrixcalc.comandos;

import com.vthmgnpipola.matrixcalc.calc.MatrizHelper;
import java.util.regex.Pattern;
import org.ejml.equation.Equation;
import org.ejml.equation.Variable;
import org.ejml.equation.VariableMatrix;
import org.ejml.equation.VariableScalar;

@ComandoRegistrado("eq")
public class ComandoEquacao implements Comando {
    private static final Pattern PATTERN = Pattern.compile("[a-zA-Z]+\s*=\s*.+");

    @Override
    public boolean checarArgumentos(String args) {
        return PATTERN.matcher(args).matches() || !args.isBlank();
    }

    @Override
    public void executar(String args) throws ComandoException {
        Equation equacao = MatrizHelper.criarEquacao();
        if (PATTERN.matcher(args).matches()) {
            String[] partes = args.split("\s*=\s*");
            String nome = partes[0];

            try {
                equacao.process(args);
            } catch (Exception e) {
                System.out.println("Ocorreu um erro processando a equação:");
                System.out.println(e.getMessage());
            }

            Variable variable = equacao.lookupVariable(nome);
            if (variable instanceof VariableMatrix) {
                MatrizHelper.registrar(nome, ((VariableMatrix) variable).matrix);
            } else if (variable instanceof VariableScalar) {
                MatrizHelper.registrar(nome, ((VariableScalar) variable).getDouble());
            } else {
                throw new ComandoException("O tipo retornado da equação não é suportado!");
            }
        } else {
            try {
                equacao.print(args);
            } catch (Exception e) {
                System.out.println("Ocorreu um erro processando a equação:");
                System.out.println(e.getMessage());
            }
        }
    }

    @Override
    public String getDescricao() {
        return """
               Executa uma equação do EJML.
               EJML é a biblioteca de manipulação de matrizes que essa calculadora de matrizes utiliza, e ela conta com
               um sistema de equações para manipular mais facilmente as matrizes.
               Para executar uma equação e imprimir o resultado do próprio EJML, insira a equação sem um nome para
               retorno (ou seja, sem um nome de matriz ou valor escalar e um símbolo '='). O resultado impresso não será
               de acordo com os padrões da calculadora de matrizes, então esteja atento a isso.
               Para executar uma equação e não imprimir o resultado, mas sim armazená-lo em uma variável (seja ele
               escalar ou uma matriz), insira o nome da variável a ser criada/atualizada e o símbolo '=', com qualquer
               quantidade de espaços entre os símbolos.""";
    }
}
