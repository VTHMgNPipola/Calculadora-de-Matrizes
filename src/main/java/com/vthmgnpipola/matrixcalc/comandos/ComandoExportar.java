package com.vthmgnpipola.matrixcalc.comandos;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@ComandoRegistrado(value = "exportar", exportavel = false)
public class ComandoExportar implements Comando {
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

        if (!Files.isRegularFile(path)) {
            if (!Files.isDirectory(path.getParent())) {
                throw new ComandoException("Arquivo e diretório parente não existentes!");
            } else {
                try {
                    Files.createFile(path);
                } catch (IOException e) {
                    throw new ComandoException("Não foi possível criar o arquivo na pasta especificada!");
                }
            }
        }

        StringBuilder sb = new StringBuilder();
        for (String comando : RegistroComandos.getHistorico()) {
            if (RegistroComandos.isExportavel(comando.split("\s+")[0])) {
                sb.append(comando).append("\n");
            }
        }

        try {
            Files.writeString(path, sb.toString());
        } catch (IOException e) {
            throw new ComandoException("Não foi possível escrever para o arquivo especificado!");
        }

        System.out.println("Comandos exportados para " + path.toString() + " com sucesso.");
        System.out.println();
    }

    @Override
    public String getDescricao() {
        return """
               Exporta todos os comandos no histórico para um arquivo separado. Pode ser útil para debugging, ou simples
               transferências de cálculos entre computadores diferentes, por exemplo.
               O local do arquivo deve ser passado como argumento, ou então executar o comando sem argumentos para
               salvar todos os comandos para o arquivo 'ComandosCM.cms' na pasta do usuário.""";
    }
}
