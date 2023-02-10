package ru.akirakozov.sd.refactoring.servlet;

import ru.akirakozov.sd.refactoring.dao.ProductDao;
import ru.akirakozov.sd.refactoring.product.Product;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.*;
import java.util.Optional;

import static ru.akirakozov.sd.refactoring.product.ProductHtml.printInfoHtml;
import static ru.akirakozov.sd.refactoring.product.ProductHtml.printProductHtml;

/**
 * @author akirakozov
 */
public class QueryServlet extends AbstractServlet {
    public QueryServlet(ProductDao productDao) {
        super(productDao);
    }

    @Override
    protected void doRequest(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException {
        String command = request.getParameter("command");

        if ("max".equals(command)) {
            Optional<Product> maxPriceProduct = productDao.findMaxPriceProduct();
            printProductHtml(maxPriceProduct, "Product with max price: ", response.getWriter());
        } else if ("min".equals(command)) {
            Optional<Product> minPriceProduct = productDao.findMinPriceProduct();
            printProductHtml(minPriceProduct, "Product with min price: ", response.getWriter());
        } else if ("sum".equals(command)) {
            long summaryPrice = productDao.getPricesSum();
            printInfoHtml(summaryPrice, "Summary price: ", response.getWriter());
        } else if ("count".equals(command)) {
            int count = productDao.getProductsCount();
            printInfoHtml(count, "Number of products: ", response.getWriter());
        } else {
            response.getWriter().println("Unknown command: " + command);
        }
    }
}
