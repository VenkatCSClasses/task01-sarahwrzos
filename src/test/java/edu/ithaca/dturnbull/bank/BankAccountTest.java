package edu.ithaca.dturnbull.bank;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


class BankAccountTest {

    @Test
    void getBalanceTest() throws InsufficientFundsException {
        // Test positive balance
        BankAccount bankAccount = new BankAccount("a@b.com", 200);
        assertEquals(200, bankAccount.getBalance(), 0.001);

        // Test zero balance
        BankAccount bankAccountZero = new BankAccount("zero@test.com", 0);
        assertEquals(0, bankAccountZero.getBalance(), 0.001);

        // Test negative balance (allowed by constructor)
        BankAccount bankAccountNegative = new BankAccount("negative@test.com", -50);
        assertEquals(-50, bankAccountNegative.getBalance(), 0.001);

        // Test balance after withdrawal
        bankAccount.withdraw(50);
        assertEquals(150, bankAccount.getBalance(), 0.001);

        // multiple calls return same value
        assertEquals(150, bankAccount.getBalance(), 0.001);
        assertEquals(150, bankAccount.getBalance(), 0.001);
    }

    @Test
    void withdrawTest() throws InsufficientFundsException{
        BankAccount bankAccount = new BankAccount("a@b.com", 200);
        //normal case
        bankAccount.withdraw(100);
        assertEquals(100, bankAccount.getBalance(), 0.001);
        
        //edge cases, too much withdrawn, negative amount withdrawn
        assertThrows(InsufficientFundsException.class, () -> bankAccount.withdraw(300));
        assertThrows( IllegalArgumentException.class, () -> bankAccount.withdraw(-100));
        assertEquals(100, bankAccount.getBalance(), 0.001);

        // too many decimal places
        assertThrows(IllegalArgumentException.class, ()-> bankAccount.withdraw(100.111));
    }

    @Test
    void isAmountValidTest(){
        // equivalence class: amount of decimal places
        // no decimal places
        float amount = 100;
        assertTrue(BankAccount.isAmountValid(amount));
        // one decimal place
        amount = (float) 100.1;
        assertTrue(BankAccount.isAmountValid(amount));
        // two decimal places
        amount = (float) 100.11;
        assertTrue(BankAccount.isAmountValid(amount));
        

        // three decimal places
        amount = (float) 100.111;
        assertFalse(BankAccount.isAmountValid(amount));

        // equivalence class: sign
        amount = (float) 100.00;
        assertTrue(BankAccount.isAmountValid(amount));
        amount = (float) 0.00;
        assertTrue(BankAccount.isAmountValid(amount));
        amount = (float) -100.00;
        assertFalse(BankAccount.isAmountValid(amount));

    }

    @Test
    void isEmailValidTest(){
        // equivalence classes
            // number of characters (empty string, shortest possible valid email, max length)
            // type of characters (special, numbers)
            // amount of each character (2 @'s, 2 .'s' next to each other)
            // position of characters


        // base cases
        assertTrue(BankAccount.isEmailValid( "a@b.com"));   // valid email address
        assertTrue( BankAccount.isEmailValid("abc@mail.com"));  // valid, no special char, not boundary case
        assertFalse( BankAccount.isEmailValid(""));         // empty string
        assertTrue( BankAccount.isEmailValid("abcdef@mail.cc"));  // valid, two letter top-level domain, boundary case
        assertTrue( BankAccount.isEmailValid("abc.def@mail.org"));  // valid, three letter top-level domain, not boundary case

        // number of characters
        assertFalse( BankAccount.isEmailValid(""));         // empty string
        assertTrue(BankAccount.isEmailValid("a@b.co"));     //shortest possible string
        // longest possible
        assertTrue(BankAccount.isEmailValid("abcdefghijabcdefghijabcdefghijabcdefghijabcdefghijabcdefghijabcdefghijabcdefghijabcdefghijabcdefghijabcdefghijabcdefghijabcdefghijabcdefghijabcdefghijabcdefghijabcdefghijabcdefghijabcdefghijabcdefghijabcdefghijabcdefghijabcdefghijabcdefghijabcdefghij@b.co"));
        // too long
        assertTrue(BankAccount.isEmailValid("aabcdefghijabcdefghijabcdefghijabcdefghijabcdefghijabcdefghijabcdefghijabcdefghijabcdefghijabcdefghijabcdefghijabcdefghijabcdefghijabcdefghijabcdefghijabcdefghijabcdefghijabcdefghijabcdefghijabcdefghijabcdefghijabcdefghijabcdefghijabcdefghijabcdefghij@b.co"));
        assertFalse( BankAccount.isEmailValid("abc.def@mail.c"));  //invalid, domain too short, not boundary case
        assertFalse( BankAccount.isEmailValid("abc.def@mail"));  // invalid, no top-level domain, not boundary case


        // type of characters
        // local part
        assertFalse( BankAccount.isEmailValid("abc-@mail.com"));  // invalid, special char at end of local part, not boundary case
        assertFalse( BankAccount.isEmailValid("abc#def@mail.com"));  // invalid, unallowed special char, not boundary case
        assertTrue( BankAccount.isEmailValid("abc-d@mail.com"));  // valid, special char in middle of local part, boundary case
        assertTrue( BankAccount.isEmailValid("abc_def@mail.com")); // valid, underscore in middle of local part, boundary case
        // domain
        assertFalse( BankAccount.isEmailValid("abc.def@mail#archive.com"));  // invalid, unallowed special char, not boundary case
        assertTrue( BankAccount.isEmailValid("abc.def@mail-archive.com"));  // valid, hyphen in middle of domain name, boundary case

        // amount of each character 
        assertFalse( BankAccount.isEmailValid("abc..def@mail.com"));  //invalid, 2 dots in a row, not boundary case
        assertTrue( BankAccount.isEmailValid("abc.def@mail.com"));  //valid,
        assertFalse( BankAccount.isEmailValid("abc.def@mail..com")); // invalid, 2 dots in a row, not boundary case

        // position of characters
        assertFalse( BankAccount.isEmailValid(".abc@mail.com"));  // invalid, starts with dot, not boundary case
        assertTrue( BankAccount.isEmailValid("abc@mail.com"));  // valid
        assertTrue( BankAccount.isEmailValid("abc.def@mail.com"));  // valid, special char in middle of local part, boundary case

    }

    @Test
    void constructorTest() {
        BankAccount bankAccount = new BankAccount("a@b.com", 200);

        assertEquals("a@b.com", bankAccount.getEmail());
        assertEquals(200, bankAccount.getBalance(), 0.001);
        //check for exception thrown correctly
        assertThrows(IllegalArgumentException.class, ()-> new BankAccount("", 100));

        //new tests
        assertThrows(IllegalArgumentException.class, ()-> new BankAccount("a@b.com", -100));
        assertThrows(IllegalArgumentException.class, ()-> new BankAccount("a@b.com", 100.111));
    }

}