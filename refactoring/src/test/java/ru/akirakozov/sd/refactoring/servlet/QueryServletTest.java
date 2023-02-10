package ru.akirakozov.sd.refactoring.servlet;

import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.SQLException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

public class QueryServletTest extends AbstractTest {

    private QueryServlet servlet;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        servlet = new QueryServlet();
    }

    @Test
    public void testMax() throws IOException, SQLException {
        when(servletRequest.getParameter("command")).thenReturn("max");

        StringWriter stringWriter = new StringWriter();
        PrintWriter printer = new PrintWriter(stringWriter);
        when(servletResponse.getWriter()).thenReturn(printer);

        servlet.doGet(servletRequest, servletResponse);

        printer.flush();
        assertThat(stringWriter.toString()).isEqualToIgnoringNewLines(
                "<html><body><h1>Product with max price:</h1>" + "iphone6\t300</br>" + "</body></html>"
        );
    }

    @Test
    public void testMin() throws IOException, SQLException {
        when(servletRequest.getParameter("command")).thenReturn("min");

        StringWriter stringWriter = new StringWriter();
        PrintWriter printer = new PrintWriter(stringWriter);
        when(servletResponse.getWriter()).thenReturn(printer);

        servlet.doGet(servletRequest, servletResponse);

        printer.flush();
        assertThat(stringWriter.toString()).isEqualToIgnoringNewLines(
                "<html><body><h1>Product with min price:</h1>" + "iphone6\t300</br>" + "</body></html>"
        );
    }

    @Test
    public void testSum() throws IOException, SQLException {
        when(servletRequest.getParameter("command")).thenReturn("sum");

        StringWriter stringWriter = new StringWriter();
        PrintWriter printer = new PrintWriter(stringWriter);
        when(servletResponse.getWriter()).thenReturn(printer);

        servlet.doGet(servletRequest, servletResponse);

        printer.flush();
        assertThat(stringWriter.toString()).isEqualToIgnoringNewLines(
                "<html><body><h1>Summary price:</h1>" + "300" + "</body></html>"
        );
    }

    @Test
    public void testCount() throws IOException, SQLException {
        when(servletRequest.getParameter("command")).thenReturn("count");

        StringWriter stringWriter = new StringWriter();
        PrintWriter printer = new PrintWriter(stringWriter);
        when(servletResponse.getWriter()).thenReturn(printer);

        servlet.doGet(servletRequest, servletResponse);

        printer.flush();
        assertThat(stringWriter.toString()).isEqualToIgnoringNewLines(
                "<html><body><h1>Number of products:</h1>" + "1" + "</body></html>"
        );
    }

}