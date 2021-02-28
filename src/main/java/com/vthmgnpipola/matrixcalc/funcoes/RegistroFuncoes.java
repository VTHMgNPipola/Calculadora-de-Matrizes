package com.vthmgnpipola.matrixcalc.funcoes;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import org.ejml.equation.ManagerFunctions;
import org.reflections.Reflections;

public class RegistroFuncoes {
    private static final Map<String, ManagerFunctions.Input1> funcoes1 = new HashMap<>();
    private static final Map<String, ManagerFunctions.InputN> funcoesN = new HashMap<>();

    public static void incluirFuncoes(ManagerFunctions managerFunctions) {
        funcoes1.forEach(managerFunctions::add1);
        funcoesN.forEach(managerFunctions::addN);
    }

    public static void registrarFuncoes() {
        funcoes1.clear();
        funcoesN.clear();

        Reflections reflections = new Reflections("com.vthmgnpipola.matrixcalc.funcoes");
        Set<Class<?>> funcoes = reflections.getTypesAnnotatedWith(FuncaoRegistrada.class);
        for (Class<?> funcao : funcoes) {
            FuncaoRegistrada funcaoRegistrada = funcao.getAnnotation(FuncaoRegistrada.class);
            String chaveFuncao = funcaoRegistrada.value();
            try {
                if (ManagerFunctions.Input1.class.isAssignableFrom(funcao)) {
                    funcoes1.put(chaveFuncao, (ManagerFunctions.Input1) funcao.getConstructor().newInstance());
                } else if (ManagerFunctions.InputN.class.isAssignableFrom(funcao)) {
                    funcoesN.put(chaveFuncao, (ManagerFunctions.InputN) funcao.getConstructor().newInstance());
                } else {
                    throw new Exception("Uma classe anotada com @FuncaoRegistrada não estende Input1 ou InputN!");
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
                throw new RuntimeException("Não foi possível instanciar a função '" + chaveFuncao + "'! Todos as" +
                        " funções devem ter um construtor sem argumentos!");
            }
        }
    }
}
