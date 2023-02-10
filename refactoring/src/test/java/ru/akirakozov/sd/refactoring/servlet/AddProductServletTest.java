package ru.akirakozov.sd.refactoring.servlet;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

public class AddProductServletTest extends AbstractTest {
    private AddProductServlet servlet;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        servlet = new AddProductServlet();
    }
    @Test
    public void testAddProductServlet() throws IOException {
        when(servletRequest.getParameter("name")).thenReturn("iphone6");
        when(servletRequest.getParameter("price")).thenReturn("300");

        StringWriter stringWriter = new StringWriter();
        PrintWriter printer = new PrintWriter(stringWriter);
        when(servletResponse.getWriter()).thenReturn(printer);

        servlet.doGet(servletRequest, servletResponse);

        assertThat(stringWriter.toString()).isEqualTo("OK" + System.lineSeparator());
    }

}
