package com.mycompany.chatapp1;

import java.util.Scanner;


public class Login {

    private static Object CurrentUser;

    private final String username;
    private final String password;
    private final String cellNumber;
    private final String name;
    private final String surname;

    public Login(String username, String password, String cellNumber, String name, String surname) {
        this.username = username;
        this.password = password;
        this.cellNumber = cellNumber;
        this.name = name;
        this.surname = surname;
    }

    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public String getCellNumber() { return cellNumber; }
    public String getName() { return name; }
    public String getSurname() { return surname; }

    public boolean checkUsername(String username) {
        return username.contains("_") && username.length() <= 5;
    }

    public boolean checkPasswordComplexity(String password) {
        return password.length() >= 8 &&
               password.matches(".*[A-Z].*") &&
               password.matches(".*[0-9].*") &&
               password.matches(".*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?].*");
    }

    public boolean checkCellPhoneNumber(String cellNumber) {
        return cellNumber != null && (
            cellNumber.matches("^0[6-8][0-9]{8}$") ||
            cellNumber.matches("^\\+27[6-8][0-9]{8}$")
        );
    }

    public String registerUser(String username, String password, String cellNumber) {
        if (!checkUsername(username)) {
            return "Username is not correctly formatted.";
        }
        if (!checkPasswordComplexity(password)) {
            return "Password is not correctly formatted.";
        }
        if (!checkCellPhoneNumber(cellNumber)) {
            return "Cell number is incorrectly formatted.";
        }
        return "User registered successfully!";
    }

    public static boolean loginUser(String inputUsername, String inputPassword, Login registeredUser) {
        return inputUsername.equals(registeredUser.getUsername()) &&
               inputPassword.equals(registeredUser.getPassword());
    }

    public String returnLoginStatus(boolean loginSuccessful) {
        return loginSuccessful ? "Login successful! Welcome." : "Login failed! Incorrect username or password.";
    }

    public static boolean performLogin(Scanner scanner, Login userLogin) {
        System.out.println("\n=== Login ===");
        int attempts = 0;
        final int MAX_ATTEMPTS = 3;

        while (attempts < MAX_ATTEMPTS) {
            System.out.print("Enter username: ");
            String inputUsername = scanner.nextLine().trim();
            System.out.print("Enter password: ");
            String inputPassword = scanner.nextLine().trim();

            if (userLogin.getUsername().equals(inputUsername) &&
                userLogin.getPassword().equals(inputPassword)) {
                
                System.out.println("Welcome, " + userLogin.getName() + " " + userLogin.getSurname() + "!");
                return true;
            } else {
                attempts++;
                System.out.println("Login failed. " + (MAX_ATTEMPTS - attempts) + " attempts remaining.");
            }
        }

        return false;
    }
}
