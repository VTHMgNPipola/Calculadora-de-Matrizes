package com.vthmgnpipola.matrixcalc.funcoes;

import org.ejml.data.DMatrixRMaj;
import org.ejml.dense.row.factory.DecompositionFactory_DDRM;
import org.ejml.equation.ManagerFunctions;
import org.ejml.equation.ManagerTempVariables;
import org.ejml.equation.Operation;
import org.ejml.equation.Variable;
import org.ejml.equation.VariableDouble;
import org.ejml.equation.VariableMatrix;
import org.ejml.equation.VariableScalar;
import org.ejml.interfaces.decomposition.LUDecomposition;

@FuncaoRegistrada("tril")
public class TrianguloL implements ManagerFunctions.Input1 {
    @Override
    public Operation.Info create(Variable A, ManagerTempVariables manager) {
        Operation.Info resultado = new Operation.Info();

        if (A instanceof VariableMatrix) {
            DMatrixRMaj matrix = ((VariableMatrix) A).matrix;
            final VariableMatrix output = manager.createMatrix();
            resultado.output = output;
            resultado.op = new Operation("tril-m") {
                @Override
                public void process() {
                    LUDecomposition<DMatrixRMaj> lu = DecompositionFactory_DDRM.lu();
                    if (!lu.decompose(matrix)) {
                        throw new RuntimeException("Ocorreu um erro ao executar a decomposição LU!");
                    }

                    output.matrix = lu.getLower(null);
                }
            };
        } else {
            final VariableDouble output = manager.createDouble();
            resultado.output = output;
            resultado.op = new Operation("tril-s") {
                @Override
                public void process() {
                    output.value = ((VariableScalar) A).getDouble();
                }
            };
        }

        return resultado;
    }
}
