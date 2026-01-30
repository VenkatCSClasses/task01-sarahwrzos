package edu.ithaca.dturnbull.bank;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.util.ArrayList;

public class BankAccount {

    private String email;
    private double balance;

    /**
     * @throws IllegalArgumentException if email is invalid
     */
    public BankAccount(String email, double startingBalance){
        if (isEmailValid(email)){
            this.email = email;
            this.balance = startingBalance;
        }
        else {
            throw new IllegalArgumentException("Email address: " + email + " is invalid, cannot create account");
        }
    }

    public double getBalance(){
        return balance;
    }

    public String getEmail(){
        return email;
    }

    /**
     * @post reduces the balance by amount if amount is non-negative and smaller than balance
     */
    public void withdraw (double amount) throws InsufficientFundsException{
        if (amount <= balance){
            balance -= amount;
        }
        else {
            throw new InsufficientFundsException("Not enough money");
        }
    }


    public static boolean isEmailValid(String email){
        if (email == null || email.isEmpty()) {
            return false;
        }
        email = email.trim();

        int atSymbolCount = 0;
        int atIndex = -1;
        for (int i = 0; i < email.length(); i++) {
            if (email.charAt(i) == '@') {
                atSymbolCount++;
                atIndex = i;
            }
        }
        if (atSymbolCount != 1) {
            return false;
        }

        String local = email.substring(0, atIndex);
        String domain = email.substring(atIndex + 1);

        if (!isValidLocal(local)) {
            return false;
        }

        if (!isValidDomain(domain)) {
            return false;
        }

        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@" +
                            "(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern pattern = Pattern.compile(emailRegex);
        return pattern.matcher(email).matches();
        
    }

    private static boolean isValidLocal(String local) {
        if (local.isEmpty()) {
            return false;
        }

        if (local.startsWith(".")|| local.endsWith(".")|| local.startsWith("-")|| local.endsWith("-")|| local.startsWith("+")|| local.endsWith("+")|| local.startsWith("&")|| local.endsWith("&")|| local.startsWith("*")|| local.endsWith("*")|| local.startsWith("_")|| local.endsWith("_")) {
            return false;
        }

        if (local.contains("..")) {
            return false;
        }

        for (char c:local.toCharArray()){
            if (isValidLocalChars(c)==false){
                return false;
            }
        }
        return true;
    }

    private static boolean isValidLocalChars(char c){
        return( (c >= 'a' && c <= 'z') ||
                (c >= 'A' && c <= 'Z') ||
                (c >= '0' && c <= '9') ||
                c == '.' || c == '_' || c == '+' || c == '&' || c == '*' || c == '-'
              );
    }

    private static boolean isValidDomain(String domain) {
        if (domain.isEmpty() || !domain.contains(".")) {
            return false;
        }
        
        String[] domainParts = domain.split("\\.");

        if (domainParts.length < 2) {
            return false;
        }

        for (int i = 0; i < domainParts.length; i++) {
            String domainString = domainParts[i];

            if (domainString.isEmpty()) {
                return false;
            }
            if (domainString.startsWith("-")|| domainString.endsWith("-")) {
                return false;
            }
            if (i == domainParts.length - 1) {
                if(domainString.length() < 2) {
                    return false;
                }
                if (!domainString.matches("[a-zA-Z]+")) {
                return false;
            }
            else {
                if (!domainString.matches("[a-zA-Z0-9-]+")) {
                    return false;
                }
            }
            }
        }
        return true;
    }
}