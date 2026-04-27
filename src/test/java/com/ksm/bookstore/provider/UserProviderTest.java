package com.ksm.bookstore.provider;

import org.mockito.InjectMocks;
import org.mockito.MockedStatic;
import org.testng.annotations.Test;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import java.io.IOException;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNull;
import static org.testng.Assert.assertTrue;

/**
 * Unit tests for {@link UserProvider}
 */
public class UserProviderTest {

    private static final class Mocking {

        @InjectMocks
        UserProvider userProvider;

        public Mocking() {
            openMocks(this);
        }

        // Helper that sets up and returns the FacesContext/ExternalContext mock chain.
        ExternalContext setupFacesMocks(MockedStatic<FacesContext> mockedFaces) {
            FacesContext facesContextMock = mock(FacesContext.class);
            ExternalContext externalContextMock = mock(ExternalContext.class);
            mockedFaces.when(FacesContext::getCurrentInstance).thenReturn(facesContextMock);
            when(facesContextMock.getExternalContext()).thenReturn(externalContextMock);
            return externalContextMock;
        }
    }

    // logout() test

    @Test(description = "logout() should invalidate the session and redirect to the home page")
    public void logout_invalidatesSessionAndRedirects() throws IOException {
        // Arrange
        Mocking m = new Mocking();

        try (MockedStatic<FacesContext> mockedFaces = mockStatic(FacesContext.class)) {
            ExternalContext externalContextMock = m.setupFacesMocks(mockedFaces);
            when(externalContextMock.getRequestContextPath()).thenReturn("");

            // Act
            m.userProvider.logout();

            // Verify
            verify(externalContextMock).invalidateSession();
            verify(externalContextMock).redirect("/pages/public/home.xhtml");
        }
    }

    // getUserName() tests

    @Test(description = "getUserName() should return the remote user from ExternalContext")
    public void getUserName_returnsRemoteUser() {
        // Arrange
        Mocking m = new Mocking();

        try (MockedStatic<FacesContext> mockedFaces = mockStatic(FacesContext.class)) {
            ExternalContext externalContextMock = m.setupFacesMocks(mockedFaces);
            when(externalContextMock.getRemoteUser()).thenReturn("test@example.com");

            // Act
            String result = m.userProvider.getUserName();

            // Assert
            assertEquals(result, "test@example.com");
        }
    }

    @Test(description = "getUserName() should return null when no user is logged in")
    public void getUserName_returnsNullWhenNoUserLoggedIn() {
        // Arrange
        Mocking m = new Mocking();

        try (MockedStatic<FacesContext> mockedFaces = mockStatic(FacesContext.class)) {
            ExternalContext externalContextMock = m.setupFacesMocks(mockedFaces);
            when(externalContextMock.getRemoteUser()).thenReturn(null);

            // Act
            String result = m.userProvider.getUserName();

            // Assert
            assertNull(result);
        }
    }

    // isAdminUser() tests

    @Test(description = "isAdminUser() should return true when the user is in the Admin role")
    public void isAdminUser_returnsTrueWhenUserIsAdmin() {
        // Arrange
        Mocking m = new Mocking();

        try (MockedStatic<FacesContext> mockedFaces = mockStatic(FacesContext.class)) {
            ExternalContext externalContextMock = m.setupFacesMocks(mockedFaces);
            when(externalContextMock.isUserInRole(AccessProvider.ADMIN)).thenReturn(true);

            // Act
            boolean result = m.userProvider.isAdminUser();

            // Assert
            assertTrue(result);
        }
    }

    @Test(description = "isAdminUser() should return false when the user is not in the Admin role")
    public void isAdminUser_returnsFalseWhenUserIsNotAdmin() {
        // Arrange
        Mocking m = new Mocking();

        try (MockedStatic<FacesContext> mockedFaces = mockStatic(FacesContext.class)) {
            ExternalContext externalContextMock = m.setupFacesMocks(mockedFaces);
            when(externalContextMock.isUserInRole(AccessProvider.ADMIN)).thenReturn(false);

            // Act
            boolean result = m.userProvider.isAdminUser();

            // Assert
            assertFalse(result);
        }
    }

    // isGeneralUser() tests

    @Test(description = "isGeneralUser() should return true when the user is in the General role")
    public void isGeneralUser_returnsTrueWhenUserIsGeneral() {
        // Arrange
        Mocking m = new Mocking();

        try (MockedStatic<FacesContext> mockedFaces = mockStatic(FacesContext.class)) {
            ExternalContext externalContextMock = m.setupFacesMocks(mockedFaces);
            when(externalContextMock.isUserInRole(AccessProvider.GENERAL)).thenReturn(true);

            // Act
            boolean result = m.userProvider.isGeneralUser();

            // Assert
            assertTrue(result);
        }
    }

    @Test(description = "isGeneralUser() should return false when the user is not in the General role")
    public void isGeneralUser_returnsFalseWhenUserIsNotGeneral() {
        // Arrange
        Mocking m = new Mocking();

        try (MockedStatic<FacesContext> mockedFaces = mockStatic(FacesContext.class)) {
            ExternalContext externalContextMock = m.setupFacesMocks(mockedFaces);
            when(externalContextMock.isUserInRole(AccessProvider.GENERAL)).thenReturn(false);

            // Act
            boolean result = m.userProvider.isGeneralUser();

            // Assert
            assertFalse(result);
        }
    }

    // isUserLoggedIn() tests

    @Test(description = "isUserLoggedIn() should return true when a remote user is present")
    public void isUserLoggedIn_returnsTrueWhenUserIsPresent() {
        // Arrange
        Mocking m = new Mocking();

        try (MockedStatic<FacesContext> mockedFaces = mockStatic(FacesContext.class)) {
            ExternalContext externalContextMock = m.setupFacesMocks(mockedFaces);
            when(externalContextMock.getRemoteUser()).thenReturn("test@example.com");

            // Act
            boolean result = m.userProvider.isUserLoggedIn();

            // Assert
            assertTrue(result);
        }
    }

    @Test(description = "isUserLoggedIn() should return false when no remote user is present")
    public void isUserLoggedIn_returnsFalseWhenNoUserPresent() {
        // Arrange
        Mocking m = new Mocking();

        try (MockedStatic<FacesContext> mockedFaces = mockStatic(FacesContext.class)) {
            ExternalContext externalContextMock = m.setupFacesMocks(mockedFaces);
            when(externalContextMock.getRemoteUser()).thenReturn(null);

            // Act
            boolean result = m.userProvider.isUserLoggedIn();

            // Assert
            assertFalse(result);
        }
    }
}