package com.example.demo.repository;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public class DBConnection {

    public static Connection getDBConnection() {
        String url = null;
        String user = null;
        String pass = null;

         url = System.getenv("JDBC_URL");
        user = System.getenv("DB_USERNAME");
        pass = System.getenv("DB_PASSWORD");

         if (url == null || user == null || pass == null) {
            try (InputStream is = DBConnection.class.getClassLoader().getResourceAsStream("application.properties")) {
                if (is != null) {
                    Properties props = new Properties();
                    props.load(is);

                     if (url == null) url = props.getProperty("spring.datasource.url");
                    if (user == null) user = props.getProperty("spring.datasource.username");
                    if (pass == null) pass = props.getProperty("spring.datasource.password");

                     if (url == null) url = props.getProperty("jdbc.url");
                    if (user == null) user = props.getProperty("jdbc.username");
                    if (pass == null) pass = props.getProperty("jdbc.password");
                }
            } catch (Exception ignored) {
             }
        }

         if (url == null || user == null || pass == null) {
            throw new RuntimeException("""
                Impossible de se connecter à la base de données.
                Veuillez définir dans application.properties :
                spring.datasource.url=jdbc:postgresql://localhost:5432/ingredient-again
                spring.datasource.username=ton_utilisateur
                spring.datasource.password=ton_mot_de_passe
                """);
        }

        try {
            return DriverManager.getConnection(url, user, pass);
        } catch (Exception e) {
            throw new RuntimeException("Erreur de connexion à PostgreSQL : " + e.getMessage(), e);
        }
    }
}