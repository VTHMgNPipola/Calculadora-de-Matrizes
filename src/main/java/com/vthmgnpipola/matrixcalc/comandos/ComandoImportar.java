package com.vthmgnpipola.matrixcalc.comandos;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

@ComandoRegistrado(value = "importar", exportavel = false)
public class ComandoImportar implements Comando {
    @Override
    public boolean checarArgumentos(String args) {
        return true;
    }

    @Override
    public void executar(String args) throws ComandoException {
        Path path;
        if (args.isEmpty()) {
            path = Path.of(System.getProperty("user.home"), "ComandosCM.cms");
        } else {
            path = Path.of(args);
        }

        List<String> comandos;
        try {
            comandos = Files.readAllLines(path);
        } catch (IOException e) {
            throw new ComandoException("Não foi possível ler o arquivo de comandos!");
        }

        PrintStream outStream = System.out;
        System.setOut(new PrintStream(new OutputStream() {
            @Override
            public void write(int b) {
                // Não faz nada, ou seja, desabilita o System.out.
            }
        }));

        // Executa todos os comandos
        for (String comando : comandos) {
            RegistroComandos.executarSeExistente(comando);
        }

        // Reabilita o System.out.
        System.setOut(outStream);

        System.out.println("Arquivo de comandos executado com sucesso!");
    }

    @Override
    public String getDescricao() {
        return """
               "Importa" comandos de um arquivo, ou seja, executa todos eles, na ordem em que estão.
               Cada comando no arquivo de comandos deve estar em uma linha separada, e devem ser escritos exatamente
               como seriam se fossem ser escritos manualmente. É útil para executar os comandos no arquivo gerado pelo
               comando 'exportar'.
               Para especificar o arquivo a ser carregado, escreva o nome do arquivo como único argumento a esse
               comando. Ou então não use nenhum argumento, e o arquivo 'ComandosCM.cms' na pasta do usuário tentará ser
               carregado.""";
    }
}
