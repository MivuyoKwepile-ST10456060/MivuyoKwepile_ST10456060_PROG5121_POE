/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.chatapp1;

import javax.swing.*;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import org.json.simple.JSONObject;



public final class Message {
    private static int messageCount = 0;
    private static final List<String> sentMessages = new ArrayList<>();

    private final String messageID;
    private final int numMessagesSent;
    private final String recipient;
    private final String message;
    private final String messageHash;

    public Message(String recipient, String message) {
        this.messageID = generateMessageID();
        this.numMessagesSent = ++messageCount;
        this.recipient = recipient;
        this.message = message;
        this.messageHash = createMessageHash();
    }


    

    private String generateMessageID() {
        Random rand = new Random();
        StringBuilder id = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            id.append(rand.nextInt(10));
        }
        return id.toString();
    }

    public boolean checkMessageID() {
        return messageID.length() == 10;
    }

    public boolean checkRecipientCell() {
         return recipient != null && (
        recipient.matches("^0[6-8][0-9]{8}$") || 
        recipient.matches("^\\+27[6-8][0-9]{8}$")
    );
    }

    public String createMessageHash() {
        String firstTwo = messageID.substring(0, 2);
        String msgNum = String.valueOf(numMessagesSent);
        String[] words = message.trim().split(" ");
        String first = words.length > 0 ? words[0] : "";
        String last = words.length > 1 ? words[words.length - 1] : "";
        return (firstTwo + ":" + msgNum + ":" + first + " " + last).toUpperCase();
    }

    public String sentMessage() {
        String[] options = {"Send Message", "Disregard Message", "Store Message to send later"};
        int choice = JOptionPane.showOptionDialog(null, "Choose an option:", "Send Message",
                JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);

        switch (choice) {
            case 0 -> {
                sentMessages.add(this.toString());
                return "Message sent";
            }
            case 1 -> {
                return "Message disregarded";
            }
            case 2 -> {
                storeMessage();
                return "Message stored";
            }
            default -> {
                return "No action taken";
            }
        }
    }

    public static String printMessages() {
        return String.join("\n", sentMessages);
    }

    public static int returnTotalMessages() {
        return messageCount;
    }

    public boolean storeMessage() {
        JSONObject json = new JSONObject();
        json.put("MessageID", messageID);
        json.put("Recipient", recipient);
        json.put("Message", message);
        json.put("MessageHash", messageHash);
        json.put("Timestamp", System.currentTimeMillis());

        try (FileWriter file = new FileWriter("messages.json", true)) {
            file.write(json.toString() + "\n");
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    @Override
    public String toString() {
        return "MessageID: " + messageID +
                "\nRecipient: " + recipient +
                "\nMessage: " + message +
                "\nMessageHash: " + messageHash;
    }

    public static void main(String[] args) {
        JOptionPane.showMessageDialog(null, "Welcome to QuickChat.");
        String inputLimit = JOptionPane.showInputDialog("How many messages would you like to send?");
        int limit = Integer.parseInt(inputLimit);

        OUTER:
        while (true) {
            String[] options = {"Send Messages", "Show Recently Sent Messages", "Quit"};
            int choice = JOptionPane.showOptionDialog(null, "Choose an option:", "Main Menu",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
            switch (choice) {
                case 0 -> {
                    if (messageCount >= limit) {
                        JOptionPane.showMessageDialog(null, "Message limit reached.");
                        continue;
                    }   String recipient = JOptionPane.showInputDialog("Enter recipient number:");
                    String message = JOptionPane.showInputDialog("Enter your message:");
                    if (message.length() > 250 || message.length() < 2) {
                        JOptionPane.showMessageDialog(null, "Please enter a message of between 2 and 250 characters.");
                        continue;
                    }   Message msg = new Message(recipient, message);
                    String result = msg.sentMessage();
                    JOptionPane.showMessageDialog(null, result);
                    JOptionPane.showMessageDialog(null, msg.toString());
                }
                case 1 -> JOptionPane.showMessageDialog(null, "Coming Soon.");
                case 2 -> {
                    JOptionPane.showMessageDialog(null, "Goodbye!");
                    break OUTER;
                }
                default -> {
                }
            }
        }

        JOptionPane.showMessageDialog(null, "Total messages sent: " + returnTotalMessages());
    }

    
}
