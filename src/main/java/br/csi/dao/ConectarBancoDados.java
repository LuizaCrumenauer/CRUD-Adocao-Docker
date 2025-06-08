package br.csi.dao;

import java.sql.SQLException;
import java.sql.Connection;
import java.sql.DriverManager;


public class ConectarBancoDados {

    // CÓDIGO MODIFICADO PARA FUNCIONAR COM DOCKER
    public static Connection conectarBancoPostgress() throws ClassNotFoundException, SQLException {
        Class.forName("org.postgresql.Driver");
        System.out.println("Driver carregado!");

        // 1. Busca as variáveis de ambiente definidas no docker-compose.yml
        // Se a variável não existir, ele usa um valor padrão (bom para rodar localmente fora do Docker)
        String dbHost = System.getenv("DB_HOST") != null ? System.getenv("DB_HOST") : "localhost";
        String dbPort = "5432"; // A porta do Postgres geralmente é padrão
        String dbName = System.getenv("DB_NAME") != null ? System.getenv("DB_NAME") : "sistema-adocao-simples";
        String dbUser = System.getenv("DB_USER") != null ? System.getenv("DB_USER") : "postgres";
        String dbPass = System.getenv("DB_PASS") != null ? System.getenv("DB_PASS") : "1234";

        // 2. Monta a URL de conexão dinamicamente
        String url = "jdbc:postgresql://" + dbHost + ":" + dbPort + "/" + dbName;

        System.out.println("Conectando a: " + url); // Log para ajudar a depurar

        // 3. Usa as variáveis para criar a conexão
        return DriverManager.getConnection(url, dbUser, dbPass);
    }
}
