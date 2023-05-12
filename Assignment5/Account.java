//Aileen Dong (ydong8@toromail.csudh.edu)
package Assignment5;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;

import static Assignment5.Bank.transactions;

public class Account {
    //Fields

    private int accountNumber;
    private String type;
    private boolean accountOpen;
    private double balance;
    private Person accountHolder;

    //Constructor
    public Account(int accountNumber, String type, Person accountHolder) {
        this.accountNumber = accountNumber;
        this.type = type;
        this.accountHolder = accountHolder;
        accountOpen = true;
    }

    public boolean withdraw(int accountNumber, double amount) throws InsufficientBalanceException, NoSuchAccountException {
        if (!isOpen()) {
            throw new NoSuchAccountException("\nAccount not found.\n\n");
        }

        if (balance < amount) {
            throw new InsufficientBalanceException("\nInsufficient balance for withdrawal!\n\n");
        }

        balance -= amount;
        return true;
    }

    public boolean deposit(double amount) {
        if (this.balance > 0) {
            return false;
        }
        this.balance = this.balance + amount;
        return true;
    }

    public boolean isOpen() {
        return this.accountOpen;
    }

    public void closeAccount() {
        this.accountOpen = false;
    }

    public double getBalance() throws NoSuchAccountException {
        return Bank.findAccount(accountNumber).getBalance();
    }

    public String toString() {
        return this.accountNumber + "(" + this.type + ")" + " : " + this.accountHolder.getFirstName() + " : " + this.accountHolder.getLastName() + " : " + this.accountHolder.getSSN();
    }

    public int getAccountNumber() {
        return accountNumber;
    }

    public String getType() {
        return this.type;
    }

    public String getAccountStatus() {
        return this.accountOpen ? "Account Open" : "Account Closed";
    }
    public void printTransactions(OutputStream out) throws IOException {

        PrintWriter writer = new PrintWriter(out);
        writer.println("\n\n------------------");

        for (Transaction t : transactions) {
            writer.println(t.toString());
        }

        writer.println("------------------");
        writer.println("Balance: " + Bank.getBalance() + "\n\n");

        writer.flush();
    }
}