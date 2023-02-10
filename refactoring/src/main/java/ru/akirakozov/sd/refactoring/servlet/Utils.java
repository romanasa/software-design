package ru.akirakozov.sd.refactoring.servlet;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Utils {

    static String createHeader(String message) {
        return "<h1>" + message + "</h1>";
    }

    static private void printlnToResponse(HttpServletResponse response, String message) throws IOException {
        response.getWriter().println(message);
    }

    static String extractNamesAndPrices(ResultSet rs) throws SQLException {
        StringBuilder res = new StringBuilder();
        while (rs.next()) {
            String name = rs.getString("name");
            int price = rs.getInt("price");
            res.append(name).append("\t").append(price).append("</br>");
        }
        return res.toString();
    }

    static void updateResponseByText(HttpServletResponse response, String text, boolean add_main_tags) throws IOException {
        if (add_main_tags) {
            printlnToResponse(response, "<html><body>");
        }
        printlnToResponse(response, text);
        if (add_main_tags) {
            printlnToResponse(response, "</body></html>");
        }

        response.setContentType("text/html");
        response.setStatus(HttpServletResponse.SC_OK);
    }
}
