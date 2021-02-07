package com.vthmgnpipola.matrixcalc.calc;

public interface CalculadoraMatrizStrategy<T> {
    T calcular(Matriz[] matrizes) throws CalculadoraException;
}
