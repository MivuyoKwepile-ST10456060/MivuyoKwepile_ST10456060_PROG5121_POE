   /*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.chatapp1;



import java.util.Scanner;

public class Login {
    
    private final String username;
    private final String password;
    private final String cellNumber;
    private final String name;
    private final String surname;
    
    public Login(String username, String password, String cellNumber, String name, String surname){
            this.username = username;
            this.password = password;
            this.cellNumber = cellNumber;
            this.name = name;
            this.surname = surname;
    
    }
    
    public String getUsername(){
        return username;
    }
    public String getPassword(){
        return password;
    }
    public String getCellNumber(){
        return cellNumber;
    }
     public String getName(){
        return name;
    }
     public String getSurname(){
        return surname;
    }
   
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
    // SA numbers: either start with 0 and followed by 9 digits, or +27 followed by 9 digits
    return cellNumber != null && (
        cellNumber.matches("^0[6-8][0-9]{8}$") ||     // e.g., 0821234567
        cellNumber.matches("^\\+27[6-8][0-9]{8}$")    // e.g., +27821234567
    );
}
    
    
    public String registerUser(String username, String password, String cellNumber) {
        if (!checkUsername(username)) {
            return "Username is not correctly formatted, please ensure that your username contains an underscore and is no more than five characters in length.";
        }
        if (!checkPasswordComplexity(password)) {
            return "Password is not correctly formatted; please ensure that the password contains at least eight characters, a capital letter, a number, and a special character.";
        }
        return "User registered successfully!";
    }

    public static boolean loginUser(String inputUsername, String inputPassword, Login registeredUser) {
        return inputUsername.equals(registeredUser.getUsername()) &&
               inputPassword.equals(registeredUser.getPassword());
    }
    
    public String returnLoginStatus(boolean loginSuccessful) {
    if (loginSuccessful) {
        return "Login successful! Welcome.";
    } else {
        return "Login failed! Incorrect username or password.";
    }
}

   public static boolean performLogin(Scanner scanner, Login userLogin) {
    System.out.println("\n=== Login ===");

    while (true) {
        System.out.print("Enter username: ");
        String inputUsername = scanner.nextLine();

        System.out.print("Enter password: ");
        String inputPassword = scanner.nextLine();

        if (userLogin.getUsername().equals(inputUsername) &&
            userLogin.getPassword().equals(inputPassword)) {
            String name;
            String surname;
            System.out.println("Welcome, " + userLogin.getName() + " " + userLogin.getSurname() + "!");

            return true;
        } else {
            System.out.println("Login failed. Please try again.");
        }
    }
}

}
    

