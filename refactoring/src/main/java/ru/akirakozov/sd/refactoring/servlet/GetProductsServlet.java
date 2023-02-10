package ru.akirakozov.sd.refactoring.servlet;

import ru.akirakozov.sd.refactoring.dao.ProductDao;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.*;

import static ru.akirakozov.sd.refactoring.product.ProductHtml.printProductsHtml;

/**
 * @author akirakozov
 */
public class GetProductsServlet extends AbstractServlet {

    public GetProductsServlet(ProductDao productDao) {
        super(productDao);
    }

    @Override
    protected void doRequest(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException {
        printProductsHtml(productDao.getProducts(), response.getWriter());
    }
}
