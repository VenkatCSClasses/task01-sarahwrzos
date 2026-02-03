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
        if (isAmountValid((float) amount)){
            this.balance += amount;
        }
        else {
            throw new IllegalArgumentException("amount not valid");
        }
    }

    /**
     * 
     * @param amount, must be valid
     * this must have enough money
     * moves money from this to other
     */
    public void transfer (double amount, BankAccount other){
        if (isAmountValid((float) amount) && amount <= this.balance){
            this.balance -= amount;
            other.balance += amount;
        }
        else{
            throw new IllegalArgumentException("amount not valid");
        }
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

    public static boolean isEmailValid(String email) {
        if (email == null) return false;

        String trimmed = email.trim();
        if (trimmed.isEmpty()) return false;

        // Split by '@', there must be exactly one '@'
        String[] parts = trimmed.split("@");
        if (parts.length != 2) return false;

        String local = parts[0];
        String domain = parts[1];

        // Local part rules
        if (!local.matches("^[a-zA-Z0-9._%+-]+$")) return false; // valid chars
        if (local.startsWith(".") || local.endsWith(".") || local.startsWith("-") || local.endsWith("-") || local.contains("..")) return false;

        // Domain rules
        String[] labels = domain.split("\\.");
        if (labels.length < 2) return false; // must have at least one dot

        for (int i = 0; i < labels.length; i++) {
            String label = labels[i];
            if (label.isEmpty()) return false;
            if (label.startsWith("-") || label.endsWith("-")) return false;

            if (i == labels.length - 1) { // TLD
                if (label.length() < 2 || !label.matches("^[a-zA-Z]+$")) return false;
            } else {
                if (!label.matches("^[a-zA-Z0-9-]+$")) return false;
            }
        }

        return true;
    }

}