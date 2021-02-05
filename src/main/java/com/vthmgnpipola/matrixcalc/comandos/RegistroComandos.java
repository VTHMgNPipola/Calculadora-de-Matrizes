package com.vthmgnpipola.matrixcalc.comandos;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import org.reflections.Reflections;

public class RegistroComandos {
    private static final Map<String, Comando> comandos = new HashMap<>();

    public static void registrarComando(String chave, Comando comando) {
        comandos.put(chave, comando);
    }

    public static Map<String, Comando> getComandos() {
        return new HashMap<>(comandos);
    }

    public static Comando procurar(String chave) {
        return comandos.get(chave);
    }

    public static void executarSeExistente(String linha) {
        String[] partes = linha.split("\s+");
        Comando comando = procurar(partes[0]);
        if (comando != null) {
            comando.executar(Arrays.copyOfRange(partes, 1, partes.length));
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
