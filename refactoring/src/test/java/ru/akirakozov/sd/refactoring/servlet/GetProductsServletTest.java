package ru.akirakozov.sd.refactoring.servlet;

import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;
import ru.akirakozov.sd.refactoring.product.Product;

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
        servlet = new GetProductsServlet(productDao);
    }
    @Test
    public void testGetProductServlet() throws IOException, SQLException {
        when(productDao.getProducts()).thenReturn(Arrays.asList(
                new Product("iphone6", 300), new Product("iphone8", 800)
        ));

        StringWriter stringWriter = new StringWriter();
        PrintWriter printer = new PrintWriter(stringWriter);
        when(servletResponse.getWriter()).thenReturn(printer);

        servlet.doGet(servletRequest, servletResponse);

        printer.flush();

        assertThat(stringWriter.toString()).isEqualToIgnoringNewLines(
                "<html><body>\n" + "iphone6\t300</br>\n" + "iphone8\t800</br>\n" + "</body></html>"
        );
    }

}