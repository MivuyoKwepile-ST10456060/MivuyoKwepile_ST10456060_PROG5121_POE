package com.mycompany.chatapp1;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class LoginTest {

    @Test
    public void testValidUsername() {
        Login login = new Login("user_", "Password1!", "0123456789", "Mivuyo", "Kwepile");
        assertTrue(login.checkUsername("user_"));
    }

    @Test
    public void testInvalidUsername_NoUnderscore() {
        Login login = new Login("user", "Password1!", "0123456789", "Mivuyo", "Kwepile");
        assertFalse(login.checkUsername("user"));
    }

    @Test
    public void testInvalidUsername_TooLong() {
        Login login = new Login("user_long", "Password1!", "0123456789", "Mivuyo", "Kwepile");
        assertFalse(login.checkUsername("user_long"));
    }

    @Test
    public void testValidPassword() {
        Login login = new Login("user_", "Password1!", "0123456789", "Mivuyo", "Kwepile");
        assertTrue(login.checkPasswordComplexity("Password1!"));
    }

    @Test
    public void testInvalidPassword_NoCapitalLetter() {
        Login login = new Login("user_", "password1!", "0123456789", "Mivuyo", "Kwepile");
        assertFalse(login.checkPasswordComplexity("password1!"));
    }

    @Test
    public void testInvalidPassword_NoNumber() {
        Login login = new Login("user_", "Password!", "0123456789", "Mivuyo", "Kwepile");
        assertFalse(login.checkPasswordComplexity("Password!"));
    }

    @Test
    public void testInvalidPassword_NoSpecialCharacter() {
        Login login = new Login("user_", "Password1", "0123456789", "Mivuyo", "Kwepile");
        assertFalse(login.checkPasswordComplexity("Password1"));
    }

    @Test
    public void testRegisterUser_Success() {
        Login login = new Login("user_", "Password1!", "0123456789", "Mivuyo", "Kwepile");
        String result = login.registerUser("user_", "Password1!", "0123456789");
        assertEquals("User registered successfully!", result);
    }

    @Test
    public void testRegisterUser_InvalidUsername() {
        Login login = new Login("user", "Password1!", "0123456789", "Mivuyo", "Kwepile");
        String result = login.registerUser("user", "Password1!", "0123456789");
        assertTrue(result.contains("Username is not correctly formatted"));
    }

    @Test
    public void testRegisterUser_InvalidPassword() {
        Login login = new Login("user_", "pass", "0123456789", "Mivuyo", "Kwepile");
        String result = login.registerUser("user_", "pass", "0123456789");
        assertTrue(result.contains("Password is not correctly formatted"));
    }

    @Test
    public void testLoginUser_Success() {
        Login registeredUser = new Login("user_", "Password1!", "0123456789", "Mivuyo", "Kwepile");
        assertTrue(Login.loginUser("user_", "Password1!", registeredUser));
    }

    @Test
    public void testLoginUser_Failure() {
        Login registeredUser = new Login("user_", "Password1!", "0123456789", "Mivuyo", "Kwepile");
        assertFalse(Login.loginUser("wrongUser", "wrongPass", registeredUser));
    }
}
