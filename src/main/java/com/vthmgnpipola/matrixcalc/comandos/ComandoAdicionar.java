package com.vthmgnpipola.matrixcalc.comandos;

@ComandoRegistrado("adicionar")
public class ComandoAdicionar implements Comando {
    @Override
    public boolean checarArgumentos(String args) {
        return args.matches("[a-zA-Z]+(\s+[a-zA-Z]+)+(\s+>\s+[a-zA-Z]+)?");
    }

    @Override
    public void executar(String args) throws ComandoException {
        new ComandoAdicionarSubtrairUtils().executar(args, false);
    }

    @Override
    public String getDescricao() {
        return """
               Adiciona matrizes, e retorna o resultado ou o armazena em ums matriz.
               Para selecionar as matrizes a serem adicionadas, só é necessário listar os seus nome como argumentos,
               separado por espaços.
               Para indicar uma matriz, onde o resultado será armazenado, utilize '> <nome-da-matriz>' após a lista
               das matrizes a serem adicionadas.""";
    }
}
