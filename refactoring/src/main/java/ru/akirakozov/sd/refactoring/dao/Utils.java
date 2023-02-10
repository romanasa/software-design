package ru.akirakozov.sd.refactoring.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Utils {

    static Connection getConnection() throws SQLException {
        String DB_URL = "jdbc:sqlite:test.db";
        return DriverManager.getConnection(DB_URL);
    }

}