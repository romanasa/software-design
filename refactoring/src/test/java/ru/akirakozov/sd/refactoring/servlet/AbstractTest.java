package ru.akirakozov.sd.refactoring.servlet;

import org.mockito.Mock;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AbstractTest {
    @Mock
    protected HttpServletRequest servletRequest;

    @Mock
    protected HttpServletResponse servletResponse;


}
