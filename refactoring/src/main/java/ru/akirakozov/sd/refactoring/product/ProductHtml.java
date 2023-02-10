package ru.akirakozov.sd.refactoring.product;

import java.io.PrintWriter;
import java.util.List;
import java.util.Optional;

public class ProductHtml {

    public static void printProductsHtml(List<Product> products, PrintWriter printer) {
        printBefore(printer);
        for (Product product : products) {
            printer.println(product.toHTML());
        }
        printAfter(printer);
    }

    public static void printProductHtml(Optional<Product> product, String header, PrintWriter printer) {
        printBefore(printer);
        printer.println(createHeader(header));
        product.ifPresent(p -> printer.println("" + p.toHTML()));
        printAfter(printer);
    }

    public static void printInfoHtml(Object info, String header, PrintWriter printer) {
        printBefore(printer);
        printer.println(header);
        printer.println(info);
        printAfter(printer);
    }

    static void printBefore(PrintWriter printer) {
        printer.println("<html>\n<body>");
    }

    static void printAfter(PrintWriter printer) {
        printer.println("</body>\n</html>");
    }

    static String createHeader(String message) {
        return "<h1>" + message + "</h1>";
    }

}
