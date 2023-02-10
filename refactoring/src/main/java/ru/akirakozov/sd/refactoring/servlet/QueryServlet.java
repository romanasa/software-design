package ru.akirakozov.sd.refactoring.servlet;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.*;

import static ru.akirakozov.sd.refactoring.servlet.Utils.*;

/**
 * @author akirakozov
 */
public class QueryServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String command = request.getParameter("command");
        String query = getQueryByCommand(command);

        String text_response;

        if (query.isEmpty()) {
            text_response = "Unknown command: " + command;
        } else {
            try (Connection c = DriverManager.getConnection("jdbc:sqlite:test.db")) {
                Statement stmt = c.createStatement();
                ResultSet rs = stmt.executeQuery(query);
                text_response = getTextResponseByCommand(command, rs);
                rs.close();
                stmt.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        updateResponseByText(response, text_response, true);
    }

    private static String getQueryByCommand(String command) {
        if ("max".equals(command)) {
            return "SELECT * FROM PRODUCT ORDER BY PRICE DESC LIMIT 1";
        } else if ("min".equals(command)) {
            return "SELECT * FROM PRODUCT ORDER BY PRICE LIMIT 1";
        } else if ("sum".equals(command)) {
            return "SELECT SUM(price) FROM PRODUCT";
        } else if ("count".equals(command)) {
            return "SELECT COUNT(*) FROM PRODUCT";
        } else {
            return "";
        }
    }

    private static String getTextResponseByCommand(String command, ResultSet rs) throws SQLException {
        StringBuilder text_response = new StringBuilder();

        if ("min".equals(command) || "max".equals(command)) {
            text_response.append(createHeader("Product with " + command + " price:"));
            text_response.append(extractNamesAndPrices(rs));
        } else if ("sum".equals(command) || "count".equals(command)) {
            String header_message = "sum".equals(command) ? "Summary price:" : "Number of products:";
            text_response.append(createHeader(header_message));
            if (rs.next()) {
                text_response.append(rs.getInt(1));
            }
        }
        return text_response.toString();
    }

}
