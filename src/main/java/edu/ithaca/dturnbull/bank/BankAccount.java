package edu.ithaca.dturnbull.bank;

public class BankAccount {

    private String email;
    private double balance;

    /**
     * @throws IllegalArgumentException if email is invalid
     */
    public BankAccount(String email, double startingBalance){
        if (isEmailValid(email) && isAmountValid((float) startingBalance)){
            this.email = email;
            this.balance = startingBalance;
        }
        else if (!isEmailValid(email)) {
            throw new IllegalArgumentException("Email address: " + email + " is invalid, cannot create account");
        }
        else {
            throw new IllegalArgumentException("starting balance not valid");

        }
    }

    public double getBalance(){
        return balance;
    }

    public String getEmail(){
        return email;
    }

    /**
     * 
     * @param amount: must be non negative and no more than 2 decimal places
     * adds to current balance
     */
    public void deposit (double amount){
        return;
    }

    /**
     * 
     * @param amount, must be valid
     * this must have enough money
     * moves money from this to other
     */
    public void transfer (double amount, BankAccount other){
        return;
    }

    /**
     * @post reduces the balance by amount if amount is non-negative and smaller than balance
     * @throws InsufficientFundsException if the balance will become negative after withdraw
     * @throws IllegalArgumentException if the withdraw amount is negative
     */
    public void withdraw (double amount) throws InsufficientFundsException{
        if (!isAmountValid((float) amount)){
            throw new IllegalArgumentException("Withdraw amount must be non-negative");
        }
        if (amount <= balance){
            balance -= amount;
        }
        else {
            throw new InsufficientFundsException("Not enough money");
        }
    }

    /**
     * 
     * @param amount
     * @return true if amount is positive and has two decimal points or less, false otherwise
     */
    public static boolean isAmountValid(float amount){
        float scaled = amount * 100;
        boolean decimal = Math.abs(scaled - Math.round(scaled)) < 0.0001;

        if (amount >= 0 && decimal){
            return true;
        }
        else{
            return false;
        }
    }

    public static boolean isEmailValid(String email){
        if (email == null || email.trim().isEmpty()) return false;
        String trimmed = email.trim();
        if (trimmed.indexOf('@') == -1 || trimmed.indexOf('@', trimmed.indexOf('@') + 1) != -1) return false;
        String[] parts = trimmed.split("@");
        String local = parts[0];
        String domain = parts[1];
        if (local.isEmpty() || domain.isEmpty()) return false;
        if (local.startsWith(".") || local.endsWith(".") || local.startsWith("-") || local.endsWith("-")) return false;
        if (local.contains("..")) return false;
        for (char c : local.toCharArray()) {
            if (!Character.isLetterOrDigit(c) && c != '.' && c != '_' && c != '%' && c != '+' && c != '-') return false;
        }
        if (!domain.contains(".")) return false;
        String[] labels = domain.split("\\.");
        for (String label : labels) {
            if (label.isEmpty()) return false;
            if (label.startsWith("-") || label.endsWith("-")) return false;
            for (char c : label.toCharArray()) {
                if (!Character.isLetterOrDigit(c) && c != '-') return false;
            }
        }
        String tld = labels[labels.length - 1];
        if (tld.length() < 2) return false;
        return true;
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