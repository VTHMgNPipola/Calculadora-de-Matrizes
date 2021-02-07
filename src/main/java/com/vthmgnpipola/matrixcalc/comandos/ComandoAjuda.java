package com.vthmgnpipola.matrixcalc.comandos;

import java.util.Map;

@ComandoRegistrado("ajuda")
public class ComandoAjuda implements Comando {
    @Override
    public boolean checarArgumentos(String args) {
        return true;
    }

    @Override
    public void executar(String args) {
        System.out.println(ANSI_BOLD + "\nComandos disponíveis e suas descrições:" + ANSI_RESET);

        Map<String, Comando> comandos = RegistroComandos.getComandos();
        for (Map.Entry<String, Comando> comando : comandos.entrySet()) {
            System.out.println(ANSI_BOLD + ANSI_YELLOW + comando.getKey() + ANSI_RESET + ": "
                    + comando.getValue().getDescricao());
        }

        System.out.println();
    }

    @Override
    public String getDescricao() {
        return "Mostra a lista de comandos e suas descrições";
    }
}
