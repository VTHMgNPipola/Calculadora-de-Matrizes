package com.vthmgnpipola.matrixcalc.comandos;

import com.vthmgnpipola.matrixcalc.calc.CalculadoraException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.reflections.Reflections;

public class RegistroComandos {
    private static final Map<String, Comando> comandos = new HashMap<>();
    private static final List<String> historico = new ArrayList<>();

    public static void registrarComando(String chave, Comando comando) {
        comandos.put(chave, comando);
    }

    public static Map<String, Comando> getComandos() {
        return new HashMap<>(comandos);
    }

    public static List<String> getHistorico() {
        return new ArrayList<>(historico);
    }

    public static Comando procurar(String chave) {
        return comandos.get(chave);
    }

    public static void executarSeExistente(String linha) {
        String[] partes = linha.split("\s+");
        Comando comando = procurar(partes[0]);
        if (comando != null) {
            String args = String.join(" ", Arrays.copyOfRange(partes, 1, partes.length));
            if (comando.checarArgumentos(args)) {
                try {
                    comando.executar(args);
                    historico.add(linha);
                } catch (ComandoException | CalculadoraException e) {
                    System.out.println("Um erro ocorreu executando o comando! Log abaixo:\n");
                    e.printStackTrace();
                }
            } else {
                System.out.println("Sintaxe do comando inválida!");
            }
        } else {
            System.out.println("Comando '" + partes[0] + "' não encontrado! Utilize o comando 'ajuda' para saber a " +
                    "lista de comandos disponíveis.");
        }
    }

    /**
     * Registra todos os comandos, ou seja, classes que implementam a interface {@link Comando}.
     */
    public static void registrarComandos() {
        comandos.clear();

        Reflections reflections = new Reflections("com.vthmgnpipola.matrixcalc.comandos");
        Set<Class<?>> comandos = reflections.getTypesAnnotatedWith(ComandoRegistrado.class);
        for (Class<?> comando : comandos) {
            if (!Comando.class.isAssignableFrom(comando)) {
                throw new RuntimeException("Classe anotada com @ComandoRegistrado não é um comando!");
            }

            ComandoRegistrado comandoRegistrado = comando.getAnnotation(ComandoRegistrado.class);
            String chaveComando = comandoRegistrado.value();
            try {
                registrarComando(chaveComando, (Comando) comando.getConstructor().newInstance());
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException("Não foi possível instanciar o comando '" + chaveComando + "'! Todos os " +
                        "comandos devem ter um construtor sem argumentos!");
            }
        }
    }
}
