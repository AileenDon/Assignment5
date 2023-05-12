//Aileen Dong (ydong8@toromail.csudh.edu)
package Assignment5;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

public class Bank {
    private static Map<Integer,Account> accounts=new TreeMap<>();
    public static ArrayList<Transaction> transactions=new ArrayList<>();
    private static int accountNumbers=100;

    public static Account openAccount(String firstName, String lastName, String SSN, String accountType) {
        Person customer=new Person(firstName, lastName,SSN);
        Account account=new Account(accountNumbers++, accountType, customer);
        accounts.put(account.getAccountNumber(),account);
        return account;
    }

    public static Account findAccount(int accountNumber) throws NoSuchAccountException{
        if(!accounts.containsKey(accountNumber)){
            throw new NoSuchAccountException("\nAccount number: "+accountNumber+" not found!\n\n");
        }
        return accounts.get(accountNumber);
    }

    public static boolean deposit(int accountNumber, double amount) throws NoSuchAccountException, AccountClosedException {
        Account a = findAccount(accountNumber);
        double balance=getBalance();

        if(!a.isOpen()&&balance>=0) {
            throw new AccountClosedException("\nAccount is closed with positive balance, deposit not allowed!\n\n");
        }

        a.deposit(amount);
        transactions.add(new Transaction(Transaction.CREDIT,amount,a.getAccountNumber()));
        return true;
    }

    public static boolean withdraw(int accountNumber, double amount) throws InsufficientBalanceException, NoSuchAccountException {
        double balance=getBalance();
        Account a = findAccount(accountNumber);

        if (a == null) {
            throw new NoSuchAccountException("\nAccount does not exist!\n\n");
        }
        if(!a.isOpen()&&balance<=0) {
            throw new InsufficientBalanceException("\nThe account is closed and balance is: "+balance+"\n\n");
        }

        a.withdraw(accountNumber,amount);
        transactions.add(new Transaction(Transaction.DEBIT,amount,a.getAccountNumber()));
        return true;
    }

    public static boolean closeAccount(int accountNumber) throws NoSuchAccountException {
        Account a = findAccount(accountNumber);

        a.closeAccount();
        return true;
    }

    public static double getBalance() {
        double remainBalance=0;

        for(Transaction t: transactions) {
            if(t.getType()==Transaction.CREDIT)remainBalance+=t.getAmount();
            else remainBalance-=t.getAmount();
        }
        return remainBalance;
    }

    public static void AccountTransactions(int accountNumber) throws NoSuchAccountException {
        Account account = findAccount(accountNumber);

        for (Transaction transaction : transactions) {
            if (transaction.accountNumber == accountNumber) {
                System.out.println(transaction.getId() + " : " +
                        (transaction.getType() == Transaction.CREDIT ? "Credit" : "Debit") +
                        " : " + transaction.getAmount());
            }
        }
        System.out.println("Balance: " + account.getBalance());
    }

    public static void printAccountTransactions(int accountNumber, OutputStream out) throws IOException,NoSuchAccountException{

        Bank.findAccount(accountNumber).printTransactions(out);
    }


}