package com.mycompany.chatapp1;

import javax.swing.*;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import org.json.simple.JSONObject;

public final class Message {
    private static int messageCount = 0;

    private static final List<String> sentMessages = new ArrayList<>();
    private static final List<Message> sentMessageObjects = new ArrayList<>();
    private static final List<String> disregardedMessages = new ArrayList<>();
    private static final List<String> storedMessages = new ArrayList<>();
    private static final List<String> messageHashes = new ArrayList<>();
    private static final List<String> messageIDs = new ArrayList<>();

    final String messageID;
    private final int numMessagesSent;
    private final String sender;
    private final String recipient;
    private final String message;
    private final String messageHash;

    public Message(String sender, String recipient, String message) {
        this.messageID = generateMessageID();
        this.numMessagesSent = ++messageCount;
        this.sender = sender;
        this.recipient = recipient;
        this.message = message;
        this.messageHash = createMessageHash();
        messageIDs.add(messageID);
        messageHashes.add(messageHash);
    }

    private String generateMessageID() {
        Random rand = new Random();
        StringBuilder id = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            id.append(rand.nextInt(10));
        }
        return id.toString();
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
                sentMessageObjects.add(this);
                return "Message sent";
            }
            case 1 -> {
                disregardedMessages.add(this.toString());
                return "Message disregarded";
            }
            case 2 -> {
                boolean stored = storeMessage();
                if (stored) {
                    storedMessages.add(this.toString());
                    return "Message stored";
                } else {
                    return "Failed to store message";
                }
            }
            default -> {
                return "No action taken";
            }
        }
    }

    public static String printMessages() {
        return sentMessages.isEmpty() ? "No messages sent yet." : String.join("\n\n", sentMessages);
    }

    public static int returnTotalMessages() {
        return messageCount;
    }

    public boolean storeMessage() {
        JSONObject json = new JSONObject();
        json.put("MessageID", messageID);
        json.put("Sender", sender);
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
                "\nSender: " + sender +
                "\nRecipient: " + recipient +
                "\nMessage: " + message +
                "\nMessageHash: " + messageHash;
    }

    // New Feature 1: Search by Message ID
    public static String searchByMessageID(String id) {
        return sentMessageObjects.stream()
                .filter(m -> m.messageID.equals(id))
                .map(Message::toString)
                .findFirst()
                .orElse("Message ID not found.");
    }

    // New Feature 2: Search by Recipient
    public static String searchByRecipient(String recipient) {
        List<Message> found = new ArrayList<>();
        for (Message m : sentMessageObjects) {
            if (m.recipient.equals(recipient)) {
                found.add(m);
            }
        }
        if (found.isEmpty()) return "No messages found for recipient: " + recipient;

        StringBuilder result = new StringBuilder();
        for (Message m : found) {
            result.append(m.toString()).append("\n\n");
        }
        return result.toString();
    }

    
    public static String longestMessage() {
        return sentMessageObjects.stream()
                .max(Comparator.comparingInt(m -> m.message.length()))
                .map(Message::toString)
                .orElse("No messages have been sent yet.");
    }

    public static void main(String[] args) {
        JOptionPane.showMessageDialog(null, "Welcome to QuickChat.");
        String inputLimit = JOptionPane.showInputDialog("How many messages would you like to send?");
        int limit = Integer.parseInt(inputLimit);

        OUTER:
        while (true) {
            String[] options = {
                    "Send Messages",
                    "Show Recently Sent Messages",
                    "Search Message by ID",
                    "Search Messages by Recipient",
                    "Show Longest Sent Message",
                    "Quit"
            };

            int choice = JOptionPane.showOptionDialog(null, "Choose an option:", "Main Menu",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);

            switch (choice) {
                case 0 -> {
                    if (messageCount >= limit) {
                        JOptionPane.showMessageDialog(null, "Message limit reached.");
                        continue;
                    }
                    String sender = JOptionPane.showInputDialog("Enter your name or number:");
                    String recipient = JOptionPane.showInputDialog("Enter recipient number:");
                    String message = JOptionPane.showInputDialog("Enter your message:");
                    if (message.length() > 250 || message.length() < 2) {
                        JOptionPane.showMessageDialog(null, "Please enter a message of between 2 and 250 characters.");
                        continue;
                    }
                    Message msg = new Message(sender, recipient, message);
                    String result = msg.sentMessage();
                    JOptionPane.showMessageDialog(null, result);
                    JOptionPane.showMessageDialog(null, msg.toString());
                }

                case 1 -> JOptionPane.showMessageDialog(null, printMessages());

                case 2 -> {
                    String id = JOptionPane.showInputDialog("Enter the Message ID to search:");
                    JOptionPane.showMessageDialog(null, searchByMessageID(id));
                }

                case 3 -> {
                    String recipient = JOptionPane.showInputDialog("Enter recipient number to search:");
                    JOptionPane.showMessageDialog(null, searchByRecipient(recipient));
                }

                case 4 -> JOptionPane.showMessageDialog(null, longestMessage());

                case 5 -> {
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
