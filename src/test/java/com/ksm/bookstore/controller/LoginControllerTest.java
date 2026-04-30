package com.ksm.bookstore.controller;

import com.ksm.bookstore.form.LoginForm;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Spy;
import org.primefaces.PrimeFaces;
import org.testng.annotations.Test;

import javax.faces.component.UIViewRoot;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import java.io.IOException;
import java.util.Locale;

import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;
import static org.testng.Assert.assertTrue;
import static org.testng.Assert.assertNull;
import static org.testng.Assert.assertFalse;

/**
 * Unit tests for {@link LoginController}
 */
public class LoginControllerTest {

    private static final class Mocking {

        @InjectMocks
        LoginController controller;

        @Mock
        FacesContext facesContext;

        @Mock
        ExternalContext externalContext;

        @Mock
        UIViewRoot viewRoot;

        @Spy
        LoginForm loginForm;

        final HttpServletRequest requestMock = mock(HttpServletRequest.class);

        final String username = "test@example.com";

        final String password = "password123";

        public Mocking() {
            openMocks(this);
            when(facesContext.getExternalContext()).thenReturn(externalContext);
            when(externalContext.getRequest()).thenReturn(requestMock);
            when(externalContext.getRequestContextPath()).thenReturn("");
            when(viewRoot.getViewId()).thenReturn("/pages/public/home.xhtml");
            when(facesContext.getViewRoot()).thenReturn(viewRoot);
            when(viewRoot.getLocale()).thenReturn(Locale.ENGLISH);
            loginForm.setUsername(username);
            loginForm.setPassword(password);
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

        // Act
        m.controller.login();

        // Verify
        verify(m.requestMock).login(m.username, m.password);
    }

    @Test(description = "login() should redirect to the current view as .jsf after a successful login")
    public void login_redirectsAfterSuccessfulLogin() throws IOException, ServletException {
        // Arrange
        Mocking m = new Mocking();

        // Act
        m.controller.login();

        // Verify
        verify(m.externalContext).redirect("/pages/public/home.jsf");
    }

    // login() tests — failed login

    @Test(description = "login() should set loginFailed to true on the form when credentials are invalid")
    public void login_setsLoginFailedOnInvalidCredentials() throws IOException, ServletException {
        // Arrange
        Mocking m = new Mocking();
        doThrow(new ServletException()).when(m.requestMock).login(m.username, m.password);

        try (MockedStatic<PrimeFaces> mockedPrimeFaces = mockStatic(PrimeFaces.class)) {
            m.setupPrimeFacesMocks(mockedPrimeFaces);

            // Act
            m.controller.login();

            // Assert
            assertTrue(m.loginForm.isLoginFailed());
        }
    }

    @Test(description = "login() should clear the password on the form when login fails")
    public void login_clearsPasswordOnFailure() throws IOException, ServletException {
        // Arrange
        Mocking m = new Mocking();
        doThrow(new ServletException()).when(m.requestMock).login(m.username, m.password);

        try (MockedStatic<PrimeFaces> mockedPrimeFaces = mockStatic(PrimeFaces.class)) {
            m.setupPrimeFacesMocks(mockedPrimeFaces);

            // Act
            m.controller.login();

            // Assert 
            assertNull(m.loginForm.getPassword());
        }
    }

    @Test(description = "login() should re-open the login dialog via PrimeFaces when login fails")
    public void login_showsLoginDialogOnFailure() throws IOException, ServletException {
        // Arrange
        Mocking m = new Mocking();
        doThrow(new ServletException()).when(m.requestMock).login(m.username, m.password);

        try (MockedStatic<PrimeFaces> mockedPrimeFaces = mockStatic(PrimeFaces.class)) {
            PrimeFaces primeFacesMock = m.setupPrimeFacesMocks(mockedPrimeFaces);
            
            // Act
            m.controller.login();

            // Verify 
            verify(primeFacesMock).executeScript("PF('loginDialog').show()");
        }
    }

    @Test(description = "clearForm() should null username, password and reset loginFailed")
    public void clearForm_resetsAllFields() {
        // Arrange
        Mocking m = new Mocking();
        m.loginForm.setLoginFailed(true);

        // Act
        m.controller.clearForm();

        // Assert
        assertNull(m.loginForm.getUsername());
        assertNull(m.loginForm.getPassword());
        assertFalse(m.loginForm.isLoginFailed());
    }
}