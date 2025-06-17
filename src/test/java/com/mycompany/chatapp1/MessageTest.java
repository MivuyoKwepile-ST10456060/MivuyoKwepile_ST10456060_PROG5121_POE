package com.mycompany.chatapp1;

import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.nio.file.Files;
import java.util.List;

import static org.junit.Assert.*;

public class MessageTest {

    private Message message;

    @Before
    public void setUp() {
        message = new Message("0123456789", "0761234567", "This is a test message");
    }

    @Test
    public void testCheckRecipientCell() {
        assertTrue(message.checkRecipientCell());

        Message invalid = new Message("0123456789", "12345", "Invalid recipient");
        assertFalse(invalid.checkRecipientCell());
    }

    @Test
    public void testCreateMessageHash() {
        String hash = message.createMessageHash();
        assertNotNull(hash);
        assertTrue(hash.matches("^[0-9]{2}:[0-9]+:.+ .+$"));
    }

    @Test
    public void testSentMessage() {
        int beforeCount = Message.returnTotalMessages();
        String result = message.sentMessage();  // Will show a dialog; ideally mock this
        int afterCount = Message.returnTotalMessages();

        assertNotNull(result);
        assertTrue(result.contains("Message"));
        assertEquals(beforeCount + 1, afterCount);
    }

    @Test
    public void testPrintMessages() {
        message.sentMessage();
        String printed = Message.printMessages();
        assertNotNull(printed);
        assertTrue(printed.contains("MessageID"));
    }

    @Test
    public void testReturnTotalMessages() {
        int initial = Message.returnTotalMessages();
        new Message("0123456789", "0761234567", "Another message").sentMessage();
        assertEquals(initial + 1, Message.returnTotalMessages());
    }

    @Test
    public void testStoreMessage() throws Exception {
        Message m = new Message("0123456789", "0761234567", "Test storing message");
        boolean stored = m.storeMessage();
        assertTrue(stored);

        File file = new File("messages.json");
        assertTrue(file.exists());

        List<String> lines = Files.readAllLines(file.toPath());
        assertTrue(lines.stream().anyMatch(line -> line.contains("Test storing message")));

        // Clean up
        file.delete();
    }

    @Test
    public void testToString() {
        String s = message.toString();
        assertTrue(s.contains("MessageID"));
        assertTrue(s.contains("Sender"));
        assertTrue(s.contains("Recipient"));
        assertTrue(s.contains("Message"));
        assertTrue(s.contains("MessageHash"));
    }

    @Test
    public void testSearchByMessageID() {
        Message m = new Message("0112233445", "0765556666", "Testing ID search");
        m.sentMessage();
        String found = Message.searchByMessageID(m.messageID);
        assertTrue(found.contains("Testing ID search"));

        String notFound = Message.searchByMessageID("nonexistentid");
        assertEquals("Message ID not found.", notFound);
    }

    @Test
    public void testSearchByRecipient() {
        Message m1 = new Message("0112233445", "0768889999", "Hi there!");
        Message m2 = new Message("0112233445", "0768889999", "Second message");
        m1.sentMessage();
        m2.sentMessage();

        String result = Message.searchByRecipient("0768889999");
        assertTrue(result.contains("Hi there!"));
        assertTrue(result.contains("Second message"));

        String noResult = Message.searchByRecipient("0000000000");
        assertEquals("No messages found for recipient: 0000000000", noResult);
    }

    @Test
    public void testLongestMessage() {
        Message shortMsg = new Message("0123", "0761112222", "Hi");
        Message longMsg = new Message("0123", "0761112222", "This is a much longer message for testing.");
        shortMsg.sentMessage();
        longMsg.sentMessage();

        String longest = Message.longestMessage();
        assertTrue(longest.contains("longer message"));
    }
}
