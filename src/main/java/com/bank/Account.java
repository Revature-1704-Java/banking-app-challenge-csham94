package com.bank;

public class Account {
    private String name;
    private double balance;
    private double interest;

    public Account() {}

    public Account(String name, double balance, double interest) {
        this.name = name;
        this.balance = balance;
        this.interest = interest;
    }

    public void setInfo(String accountLine) {
        String[] parts = accountLine.split(",");
        this.name = parts[0].trim();
        this.balance = Double.parseDouble(parts[1]);
        this.interest = Double.parseDouble(parts[2]);
    }

    public void getInfo(String name) {
        System.out.println("ACCOUNT NAME: " + name);
        System.out.println("ACCOUNT BALANCE: " + String.format("$%,.2f", balance));
        System.out.println("ACCOUNT INTEREST RATE: " + String.format("%,.2f", interest) + "%");
        System.out.println("");
    }

    public boolean checkName(String name) {
        String accountName = this.name;
        accountName = accountName.toLowerCase();
        String lowerName = name.toLowerCase();
        if (lowerName.equals(accountName)) {
            return true;
        } else {
            return false;
        }
    }

    public void withdraw(double amount) {
        double newBalance;
        newBalance = (balance - amount);
        if (newBalance < 0) {
            System.out.println("Cannot withdraw more than is deposited.");
        } else {
            System.out.println("Withdrew " + String.format("$%,.2f", amount) + " from balance of " + String.format("$%,.2f", balance));
            this.balance = newBalance;
            System.out.println("Your new balance is: " + String.format("$%,.2f", balance));
        }
    }

    public void deposit(double amount) {
        double newBalance;
        newBalance = (balance + amount);
        System.out.println("Deposited " + String.format("$%,.2f", amount) + " from balance of " + String.format("%,.2f", balance));
        this.balance = newBalance;
        System.out.println("Your new balance is: " + String.format("$%,.2f", balance));
    }

    public String prepareLine() {
        String newLine = this.name + "," + this.balance + "," + this.interest + ";" + "\n";
        return newLine;
    }
}