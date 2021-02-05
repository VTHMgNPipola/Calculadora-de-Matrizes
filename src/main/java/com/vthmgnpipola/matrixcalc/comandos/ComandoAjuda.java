package com.vthmgnpipola.matrixcalc.comandos;

import java.util.Map;

@ComandoRegistrado("ajuda")
public class ComandoAjuda implements Comando {
    @Override
    public void executar(String[] args) {
        System.out.println("\nComandos disponíveis e suas descrições:");

        Map<String, Comando> comandos = RegistroComandos.getComandos();
        for (Map.Entry<String, Comando> comando : comandos.entrySet()) {
            System.out.println(comando.getKey() + ": " + comando.getValue().getDescricao());
        }

        System.out.println();
    }

    @Override
    public String getDescricao() {
        return "Mostra a lista de comandos e suas descrições";
    }
}
