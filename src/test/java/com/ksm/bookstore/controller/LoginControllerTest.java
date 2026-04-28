package com.ksm.bookstore.controller;

import com.ksm.bookstore.form.LoginForm;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.primefaces.PrimeFaces;
import org.testng.annotations.Test;

import javax.faces.component.UIViewRoot;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import java.io.IOException;

import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

/**
 * Unit tests for {@link LoginController}
 */
public class LoginControllerTest {

    private static final class Mocking {

        @InjectMocks
        LoginController controller;

        @Mock
        LoginForm loginForm;

        final HttpServletRequest requestMock = mock(HttpServletRequest.class);

        final UIViewRoot viewRootMock = mock(UIViewRoot.class);

        final String username = "test@example.com";

        final String password = "password123";

        public Mocking() {
            openMocks(this);
            when(loginForm.getUsername()).thenReturn(username);
            when(loginForm.getPassword()).thenReturn(password);
        }

        // Wires the full FacesContext chain that login() depends on
        ExternalContext setupFacesMocks(MockedStatic<FacesContext> mockedFaces) {
            FacesContext facesContextMock = mock(FacesContext.class);
            ExternalContext externalContextMock = mock(ExternalContext.class);
            mockedFaces.when(FacesContext::getCurrentInstance).thenReturn(facesContextMock);
            when(facesContextMock.getExternalContext()).thenReturn(externalContextMock);
            when(externalContextMock.getRequest()).thenReturn(requestMock);
            when(externalContextMock.getRequestContextPath()).thenReturn("");
            when(facesContextMock.getViewRoot()).thenReturn(viewRootMock);
            when(viewRootMock.getViewId()).thenReturn("/pages/public/home.xhtml");

            return externalContextMock;
        }

        // Wires PrimeFaces.current() — only needed by failure-path tests
        PrimeFaces setupPrimeFacesMocks(MockedStatic<PrimeFaces> mockedPrimeFaces) {
            PrimeFaces primeFacesMock = mock(PrimeFaces.class);
            mockedPrimeFaces.when(PrimeFaces::current).thenReturn(primeFacesMock);
            return primeFacesMock;
        }
    }

    // login() tests — successful login

    @Test(description = "login() should call request.login() with the username and password from the form")
    public void login_callsRequestLoginWithCredentials() throws IOException, ServletException {
        // Arrange
        Mocking m = new Mocking();

        try (MockedStatic<FacesContext> mockedFaces = mockStatic(FacesContext.class)) {
            m.setupFacesMocks(mockedFaces);

            // Act
            m.controller.login();

            // Verify
            verify(m.requestMock).login(m.username, m.password);
        }
    }

    @Test(description = "login() should redirect to the current view as .jsf after a successful login")
    public void login_redirectsAfterSuccessfulLogin() throws IOException, ServletException {
        // Arrange
        Mocking m = new Mocking();

        try (MockedStatic<FacesContext> mockedFaces = mockStatic(FacesContext.class)) {
            ExternalContext externalContextMock = m.setupFacesMocks(mockedFaces);

            // Act
            m.controller.login();

            // Verify
            verify(externalContextMock).redirect("/pages/public/home.jsf");
        }
    }

    // login() tests — failed login

    @Test(description = "login() should set loginFailed to true on the form when credentials are invalid")
    public void login_setsLoginFailedOnInvalidCredentials() throws IOException, ServletException {
        // Arrange
        Mocking m = new Mocking();
        doThrow(new ServletException()).when(m.requestMock).login(m.username, m.password);

        try (MockedStatic<FacesContext> mockedFaces = mockStatic(FacesContext.class);
             MockedStatic<PrimeFaces> mockedPrimeFaces = mockStatic(PrimeFaces.class)) {
            m.setupFacesMocks(mockedFaces);
            m.setupPrimeFacesMocks(mockedPrimeFaces);

            // Act
            m.controller.login();

            // Verify
            verify(m.loginForm).setLoginFailed(true);
        }
    }

    @Test(description = "login() should clear the password on the form when login fails")
    public void login_clearsPasswordOnFailure() throws IOException, ServletException {
        // Arrange
        Mocking m = new Mocking();
        doThrow(new ServletException()).when(m.requestMock).login(m.username, m.password);

        try (MockedStatic<FacesContext> mockedFaces = mockStatic(FacesContext.class);
             MockedStatic<PrimeFaces> mockedPrimeFaces = mockStatic(PrimeFaces.class)) {
            m.setupFacesMocks(mockedFaces);
            m.setupPrimeFacesMocks(mockedPrimeFaces);

            // Act
            m.controller.login();

            // Verify 
            verify(m.loginForm).setPassword(null);
        }
    }

    @Test(description = "login() should re-open the login dialog via PrimeFaces when login fails")
    public void login_showsLoginDialogOnFailure() throws IOException, ServletException {
        // Arrange
        Mocking m = new Mocking();
        doThrow(new ServletException()).when(m.requestMock).login(m.username, m.password);

        try (MockedStatic<FacesContext> mockedFaces = mockStatic(FacesContext.class);
             MockedStatic<PrimeFaces> mockedPrimeFaces = mockStatic(PrimeFaces.class)) {
            m.setupFacesMocks(mockedFaces);
            PrimeFaces primeFacesMock = m.setupPrimeFacesMocks(mockedPrimeFaces);

            // Act
            m.controller.login();

            // Verify 
            verify(primeFacesMock).executeScript("PF('loginDialog').show()");
        }
    }
}