package ru.akirakozov.sd.refactoring.servlet;

import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.SQLException;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

public class GetProductsServletTest extends AbstractTest{

    private GetProductsServlet servlet;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        servlet = new GetProductsServlet();
    }
    @Test
    public void testGetProductServlet() throws IOException, SQLException {
        StringWriter stringWriter = new StringWriter();
        PrintWriter printer = new PrintWriter(stringWriter);
        when(servletResponse.getWriter()).thenReturn(printer);

        servlet.doGet(servletRequest, servletResponse);

        printer.flush();

        assertThat(stringWriter.toString()).isEqualToIgnoringNewLines(
                "<html><body>\n" + "iphone6\t300</br>\n" + "</body></html>"
        );
    }

}