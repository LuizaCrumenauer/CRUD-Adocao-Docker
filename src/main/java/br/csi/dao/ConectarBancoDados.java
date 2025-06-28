package br.csi.dao;

import java.sql.SQLException;
import java.sql.Connection;
import java.sql.DriverManager;


public class ConectarBancoDados {

    public static Connection conectarBancoPostgress() throws ClassNotFoundException, SQLException {
        Class.forName("org.postgresql.Driver");
        System.out.println("Driver carregado!");

        String dbHost = System.getenv("DB_HOST") != null ? System.getenv("DB_HOST") : "localhost";
        String dbPort = "5432";
        String dbName = System.getenv("DB_NAME") != null ? System.getenv("DB_NAME") : "sistema-adocao-simples";
        String dbUser = System.getenv("DB_USER") != null ? System.getenv("DB_USER") : "postgres";
        String dbPass = System.getenv("DB_PASS") != null ? System.getenv("DB_PASS") : "1234";

        String url = "jdbc:postgresql://" + dbHost + ":" + dbPort + "/" + dbName;

        System.out.println("Conectando a: " + url);

        return DriverManager.getConnection(url, dbUser, dbPass);
    }
}
