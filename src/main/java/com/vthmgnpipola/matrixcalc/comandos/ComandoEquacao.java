package com.vthmgnpipola.matrixcalc.comandos;

import com.vthmgnpipola.matrixcalc.calc.MatrizHelper;
import java.util.regex.Pattern;
import org.ejml.equation.Equation;
import org.ejml.equation.Variable;
import org.ejml.equation.VariableMatrix;
import org.ejml.equation.VariableScalar;

@ComandoRegistrado("eq")
public class ComandoEquacao implements Comando {
    private static final Pattern PATTERN = Pattern.compile("[a-zA-Z]\s*=\s*.+");

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

            equacao.process(args);

            Variable variable = equacao.lookupVariable(nome);
            if (variable instanceof VariableMatrix) {
                MatrizHelper.registrar(nome, ((VariableMatrix) variable).matrix);
            } else if (variable instanceof VariableScalar) {
                MatrizHelper.registrar(nome, ((VariableScalar) variable).getDouble());
            } else {
                throw new ComandoException("O tipo retornado da equação não é suportado!");
            }
        } else {
            equacao.print(args);
        }
    }

    @Override
    public String getDescricao() {
        return null;
    }
}
