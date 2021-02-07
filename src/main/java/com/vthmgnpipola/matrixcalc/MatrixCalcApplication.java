package com.vthmgnpipola.matrixcalc;

import com.vthmgnpipola.matrixcalc.calc.RegistroMatrizes;
import com.vthmgnpipola.matrixcalc.comandos.Comando;
import com.vthmgnpipola.matrixcalc.comandos.RegistroComandos;
import java.util.Scanner;

public class MatrixCalcApplication {
    private static boolean executando = true;

    public static void main(String[] args) {
        String header = """
                   ______      __           __          __                        __        __  ___      __       _               \s
                  / ____/___ _/ /______  __/ /___ _____/ /___  _________ _   ____/ /__     /  |/  /___ _/ /______(_)___  ___  _____
                 / /   / __ `/ / ___/ / / / / __ `/ __  / __ \\/ ___/ __ `/  / __  / _ \\   / /|_/ / __ `/ __/ ___/ /_  / / _ \\/ ___/
                / /___/ /_/ / / /__/ /_/ / / /_/ / /_/ / /_/ / /  / /_/ /  / /_/ /  __/  / /  / / /_/ / /_/ /  / / / /_/  __(__  )\s
                \\____/\\__,_/_/\\___/\\__,_/_/\\__,_/\\__,_/\\____/_/   \\__,_/   \\__,_/\\___/  /_/  /_/\\__,_/\\__/_/  /_/ /___/\\___/____/ \s
                """;

        System.out.println(header);
        System.out.println("Deixando cálculos irritantes de ensino médio um pouco menos chatos.");
        System.out.println("Digite 'ajuda' para exibir a lista de comandos.\n\n\n");

        RegistroComandos.registrarComandos();

        Scanner scanner = new Scanner(System.in);
        while (executando) {
            int quantidadeMatrizes = RegistroMatrizes.getQuantidadeMatrizes();
            System.out.print(Comando.ANSI_BOLD + Comando.ANSI_BLUE + quantidadeMatrizes + Comando.ANSI_RESET +
                    Comando.ANSI_BLUE + (quantidadeMatrizes == 1 ? " matriz registrada" : " matrizes registradas") +
                    Comando.ANSI_RESET + Comando.ANSI_BOLD + Comando.ANSI_RED + " > " + Comando.ANSI_RESET);
            String linha = scanner.nextLine();

            RegistroComandos.executarSeExistente(linha);
        }
    }

    public static void sair() {
        executando = false;
    }
}
