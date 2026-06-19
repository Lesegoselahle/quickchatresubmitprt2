/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Student
 */
public class tests {
    kage com.mycompany.quickchat;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

public class QuickchatTest {

    private Quickchat.Login login;

    @BeforeEach
    void setUp() {
        login = new Quickchat.Login();
    }

    // Test 1: Username format - 1-5 chars, alphanumeric + underscore
    @Test
    void testCheckUsername_Valid() {
        assertTrue(login.checkUsername("user_"));
        assertTrue(login.checkUsername("abc12"));
    }

    @Test
    void testCheckUsername_Invalid() {
        assertFalse(login.checkUsername("abcdef")); // 6 chars > 5
        assertFalse(login.checkUsername("user@")); // special char not _
        assertFalse(login.checkUsername("")); // empty
        assertFalse(login.checkUsername(null));
    }

    // Test 2: Password complexity - 8 chars, 1 cap, 1 digit, 1 special
    @Test
    void testCheckPasswordComplexity_Valid() {
        assertTrue(login.checkPasswordComplexity("Pass123!"));
        assertTrue(login.checkPasswordComplexity("A1@bcdef"));
    }

    @Test
    void testCheckPasswordComplexity_Invalid() {
        assertFalse(login.checkPasswordComplexity("pass123!")); // no uppercase
        assertFalse(login.checkPasswordComplexity("Pass1234")); // no special char
        assertFalse(login.checkPasswordComplexity("Pass!@#")); // no digit + <8 chars
        assertFalse(login.checkPasswordComplexity(null));
    }

    // Test 3: Cell phone +27 + 9 digits
    @Test
    void testCheckCellPhoneNumber_Valid() {
        assertTrue(login.checkCellPhoneNumber("+27831234567"));
        assertTrue(login.checkCellPhoneNumber("+27123456789"));
    }

    @Test
    void testCheckCellPhoneNumber_Invalid() {
        assertFalse(login.checkCellPhoneNumber("0831234567")); // no +27
        assertFalse(login.checkCellPhoneNumber("+2783123456")); // only 8 digits
        assertFalse(login.checkCellPhoneNumber("+278312345678")); // 10 digits
        assertFalse(login.checkCellPhoneNumber(null));
    }

    // Test 4: Register user - matches your success message
    @Test
    void testRegisterUser_Success() {
        String result = login.registerUser("user1", "Pass123!", "+27831234567");
        assertEquals("Username successfully captured.", result);
    }

    @Test
    void testRegisterUser_FailUsername() {
        String result = login.registerUser("toolong", "Pass123!", "+27831234567");
        assertTrue(result.contains("Username is not correctly formatted"));
    }

    // Test 5: Login user
    @Test
    void testLoginUser_Success() {
        login.registerUser("user1", "Pass123!", "+27831234567");
        assertTrue(login.loginUser("user1", "Pass123!"));
    }

    @Test
    void testLoginUser_Fail() {
        login.registerUser("user1", "Pass123!", "+27831234567");
        assertFalse(login.loginUser("user1", "WrongPass"));
    }

    // Test 6: Message length <= 250 chars - from your screenshot req
    @Test
    void testCheckMessageLength_Valid() {
        String msg = "a".repeat(250);
        assertTrue(Quickchat.checkMessageLength(msg));
    }

    @Test
    void testCheckMessageLength_Invalid() {
        String msg = "a".repeat(251);
        assertFalse(Quickchat.checkMessageLength(msg));
    }

    // Test 7: Recipient format +27 + 9 digits
    @Test
    void testCheckRecipientCell_Valid() {
        assertTrue(Quickchat.checkRecipientCell("+27831234567"));
    }

    @Test
    void testCheckRecipientCell_Invalid() {
        assertFalse(Quickchat.checkRecipientCell("+2783123456"));
        assertFalse(Quickchat.checkRecipientCell("0831234567"));
    }

    // Test 8: Message Hash format - first2:msgNum:FIRSTLAST all caps
    // From your screenshot: 00:0:HITHANKS
    @Test
    void testGenerateMessageHash() {
        String msgID = "0123456789";
        int msgNum = 5;
        String content = "Hi there thanks";
        String expected = "01:5:HITHANKS";
        assertEquals(expected, Quickchat.generateMessageHash(msgID, msgNum, content));
    }

    @Test
    void testGenerateMessageHash_SingleWord() {
        String msgID = "9912345678";
        int msgNum = 1;
        String content = "Hello";
        String expected = "99:1:HELLOHELLO"; // first and last word same
        assertEquals(expected, Quickchat.generateMessageHash(msgID, msgNum, content));
    }
}
