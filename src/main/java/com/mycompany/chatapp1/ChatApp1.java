package com.mycompany.chatapp1;

import static com.mycompany.chatapp1.Login.performLogin;
import java.util.Scanner;
import java.util.regex.Pattern;
import javax.swing.JOptionPane;

public class ChatApp1 {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        String username;
        String password;
        String cellNumber;

        //Registration
        
        System.out.println("=== Register ===");
        
        System.out.print("Enter your name: ");
        String name = scanner.nextLine();

        System.out.print("Enter your surname: ");
        String surname = scanner.nextLine();
        
        // Prompt for username until valid
        while (true) {
            System.out.print("Enter username: ");
            username = scanner.nextLine();
            String usernameMessage = validateUsername(username);
            if (usernameMessage.equals("Username successfully captured.")) {
                System.out.println(usernameMessage);
                break;
            } else {
                System.out.println(usernameMessage);
            }
        }

        // Prompt for password until valid
        while (true) {
            System.out.print("Enter password: ");
            password = scanner.nextLine();
            String passwordMessage = validatePassword(password);
            if (passwordMessage.equals("Password successfully captured.")) {
                System.out.println(passwordMessage);
                break;
            } else {
                System.out.println(passwordMessage);
            }
        }

        // Prompt for cell number until valid
        while (true) {
            System.out.print("Enter South African cell phone number: ");
            cellNumber = scanner.nextLine();
            String cellNumberMessage = validateCellNumber(cellNumber);
            if (cellNumberMessage.equals("Phone number successfully captured.")) {
                System.out.println(cellNumberMessage);
                break;
            } else {
                System.out.println(cellNumberMessage);
            }
        }
        
        // Create Login object with registered details
        Login userLogin = new Login(username, password, cellNumber,name, surname);
        System.out.println("\nRegistration completed successfully!");

        boolean loggedIn = performLogin(scanner, userLogin);

        if (loggedIn) {
            System.out.println("\nLogin successful! Launching QuickChat Messaging...");
            JOptionPane.showMessageDialog(null, "QuickChat launched successfully.");
            Message.main(new String[]{});  // This should run the Message GUI
        } else {
          System.out.println("Login failed. Exiting...");
        }

        scanner.close();
    }

    public static String validateUsername(String username) {
        if (username != null && username.length() <= 5 && username.contains("_")) {
            return "Username successfully captured.";
        } else {
            return "Username is not correctly formatted, please ensure that your username contains an underscore and is no more than five characters in length.";
        }
    }

    public static String validatePassword(String password) {
        boolean hasMinLength = password != null && password.length() >= 8;
        boolean hasCapital = password != null && !password.equals(password.toLowerCase());
        boolean hasNumber = password != null && password.matches(".*\\d.*");
        boolean hasSpecial = password != null && !password.matches("[A-Za-z0-9]*");

        if (hasMinLength && hasCapital && hasNumber && hasSpecial) {
            return "Password successfully captured.";
        } else {
            return "Password is not correctly formatted; please ensure that the password contains at least eight characters, a capital letter, a number, and a special character.";
        }
    }

    public static String validateCellNumber(String cellNumber) {
        String regex = "^(\\+27|0)[0-9]{9}$";
        if (cellNumber != null && Pattern.matches(regex, cellNumber)) {
            return "Phone number successfully captured.";
        } else {
            return "Cell number is incorrectly formatted or does not contain a international code. Please enter in format +27XXXXXXXXX or 0XXXXXXXXX (9 digits after +27 or 9 digits after 0).";
        }
    }

    
    
}
