package ru.akirakozov.sd.refactoring.servlet;

import ru.akirakozov.sd.refactoring.dao.ProductDao;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public abstract class AbstractServlet extends HttpServlet {
    protected final ProductDao productDao;

    public AbstractServlet(ProductDao productDao) {
        this.productDao = productDao;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        try {
            doRequest(request, response);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    abstract void doRequest(HttpServletRequest request, HttpServletResponse response) throws Exception;
}
