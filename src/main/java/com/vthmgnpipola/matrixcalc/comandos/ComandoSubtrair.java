package com.vthmgnpipola.matrixcalc.comandos;

@ComandoRegistrado("subtrair")
public class ComandoSubtrair implements Comando {
    @Override
    public boolean checarArgumentos(String args) {
        return args.matches("[a-zA-Z]+(\s+[a-zA-Z]+)+(\s+-para\s+[a-zA-Z]+)?");
    }

    @Override
    public void executar(String args) throws ComandoException {
        new ComandoAdicionarSubtrairUtils().executar(args, true);
    }

    @Override
    public String getDescricao() {
        return """
               Subtrai matrizes, e retorna o resultado ou o armazena em uma outra matriz.
               Para selecionar as matrizes a serem subtraídas, só é necessário listar os seus nome como argumentos,
               separado por espaços.
               Para indicar uma matriz, onde o resultado será armazenado, utilize '-para <nome-da-matriz>' após a lista
               das matrizes a serem adicionadas.""";
    }
}
