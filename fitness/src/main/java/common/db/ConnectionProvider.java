package common.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public class ConnectionProvider {
    private final static String DB = "jdbc:postgresql://localhost:5432/fitness";
    private static final String USER = "postgres";
    private static final String PASSWORD = "admin";

    public static Connection connect() throws Exception {
        Properties props = new Properties();
        props.setProperty("user", USER);
        props.setProperty("password", PASSWORD);

        Class.forName("org.postgresql.Driver");
        return DriverManager.getConnection(DB, props);
    }
}
