package edu.ithaca.dturnbull.bank;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


class BankAccountTest {

    @Test
    void getBalanceTest() {
        BankAccount bankAccount = new BankAccount("a@b.com", 200);

        assertEquals(200, bankAccount.getBalance(), 0.001);
    }

    @Test
    void withdrawTest() throws InsufficientFundsException{
        BankAccount bankAccount = new BankAccount("a@b.com", 200);
        bankAccount.withdraw(100);

        assertEquals(100, bankAccount.getBalance(), 0.001);
        assertThrows(InsufficientFundsException.class, () -> bankAccount.withdraw(300));
    }

    @Test
    void isEmailValidTest(){
        assertTrue(BankAccount.isEmailValid( "a@b.com"));   // valid email address
        assertFalse( BankAccount.isEmailValid(""));         // empty string

        assertFalse( BankAccount.isEmailValid("abc-@mail.com"));  // invalid, special char at end of local part, not boundary case
        assertFalse( BankAccount.isEmailValid("abc..def@mail.com"));  //invalid, 2 dots in a row, not boundary case
        assertFalse( BankAccount.isEmailValid(".abc@mail.com"));  // invalid, starts with dot, not boundary case
        assertFalse( BankAccount.isEmailValid("abc#def@mail.com"));  // invalid, unallowed special char, not boundary case

        assertTrue( BankAccount.isEmailValid("abc-d@mail.com"));  // valid, special char in middle of local part, boundary case
        assertTrue( BankAccount.isEmailValid("abc.def@mail.com"));  // valid, special char in middle of local part, boundary case
        assertTrue( BankAccount.isEmailValid("abc@mail.com"));  // valid, no special char, not boundary case
        assertTrue( BankAccount.isEmailValid("abc_def@mail.com")); // valid, underscore in middle of local part, boundary case
        
        assertFalse( BankAccount.isEmailValid("abc.def@mail.c"));  //invalid, domain too short, not boundary case
        assertFalse( BankAccount.isEmailValid("abc.def@mail#archive.com"));  // invalid, unallowed special char, not boundary case
        assertFalse( BankAccount.isEmailValid("abc.def@mail"));  // invalid, no top-level domain, not boundary case
        assertFalse( BankAccount.isEmailValid("abc.def@mail..com")); // invalid, 2 dots in a row, not boundary case

        assertTrue( BankAccount.isEmailValid("abc.def@mail.cc"));  // valid, two letter top-level domain, boundary case
        assertTrue( BankAccount.isEmailValid("abc.def@mail-archive.com"));  // valid, hyphen in middle of domain name, boundary case
        assertTrue( BankAccount.isEmailValid("abc.def@mail.org"));  // valid, three letter top-level domain, not boundary case
        assertTrue( BankAccount.isEmailValid("abc.def@mail.com")); // valid, common top-level domain, not boundary case
        // no tests for lengths
    }

    @Test
    void constructorTest() {
        BankAccount bankAccount = new BankAccount("a@b.com", 200);

        assertEquals("a@b.com", bankAccount.getEmail());
        assertEquals(200, bankAccount.getBalance(), 0.001);
        //check for exception thrown correctly
        assertThrows(IllegalArgumentException.class, ()-> new BankAccount("", 100));
    }

}