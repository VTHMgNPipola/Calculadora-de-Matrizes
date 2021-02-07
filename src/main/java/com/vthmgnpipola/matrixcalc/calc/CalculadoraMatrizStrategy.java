package com.vthmgnpipola.matrixcalc.calc;

public interface CalculadoraMatrizStrategy<T, O> {
    T calcular(Matriz[] matrizes, O opts) throws CalculadoraException;
}
