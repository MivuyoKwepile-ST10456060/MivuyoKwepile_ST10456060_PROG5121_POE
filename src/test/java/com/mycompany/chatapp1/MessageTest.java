/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package com.mycompany.chatapp1;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author RC_Student_lab
 */
public class MessageTest {
    
    public MessageTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }

    @Test
    public void testCheckMessageID() {
        System.out.println("checkMessageID");
        Message instance = null;
        boolean expResult = false;
        boolean result = instance.checkMessageID();
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    @Test
    public void testCheckRecipientCell() {
        System.out.println("checkRecipientCell");
        Message instance = null;
        boolean expResult = false;
        boolean result = instance.checkRecipientCell();
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    @Test
    public void testCreateMessageHash() {
        System.out.println("createMessageHash");
        Message instance = null;
        String expResult = "";
        String result = instance.createMessageHash();
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    @Test
    public void testSentMessage() {
        System.out.println("sentMessage");
        Message instance = null;
        String expResult = "";
        String result = instance.sentMessage();
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    @Test
    public void testPrintMessages() {
        System.out.println("printMessages");
        String expResult = "";
        String result = Message.printMessages();
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    @Test
    public void testReturnTotalMessages() {
        System.out.println("returnTotalMessages");
        int expResult = 0;
        int result = Message.returnTotalMessages();
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    @Test
    public void testStoreMessage() {
        System.out.println("storeMessage");
        Message instance = null;
        boolean expResult = false;
        boolean result = instance.storeMessage();
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    @Test
    public void testToString() {
        System.out.println("toString");
        Message instance = null;
        String expResult = "";
        String result = instance.toString();
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    @Test
    public void testMain() {
        System.out.println("main");
        String[] args = null;
        Message.main(args);
        fail("The test case is a prototype.");
    }
    
}
