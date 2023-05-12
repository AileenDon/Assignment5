//Aileen Dong (ydong8@toromail.csudh.edu)
package Assignment5;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Scanner;

public class Person {

    //Fields
    private String firstName;
    private String lastName;
    private String SSN;
    private int id;

    //Constructors
    public Person(String fName, String lName,  String SSN) {
        firstName=fName;
        lastName=lName;
        this.SSN=SSN;
        this.id=id;
    }

    //Methods

    public  String getFirstName() {
        return firstName;
    }
    public String getLastName() {
        return lastName;
    }
    public String getSSN() {
        return SSN;
    }

    @Override
    public String toString() {
        return firstName+":"+lastName+":"+SSN;
    }

    public static void main(String[] args) throws InsufficientBalanceException, AccountClosedException, NoSuchAccountException {

        Scanner scanner = new Scanner(System.in);
        boolean exit = false;

        while (!exit) {

            // Display menu of options
            System.out.println("Please choose an option:");
            System.out.println("1 - Open a Checking account");
            System.out.println("2 - Open Saving Account");
            System.out.println("3 - List Accounts");
            System.out.println("4 - Account Statement");
            System.out.println("5 - Deposit funds");
            System.out.println("6 - Withdraw funds");
            System.out.println("7 - Close an account");
            System.out.println("8 - Save Transactions");
            System.out.println("9 - Exit");

            // Get user input
            int choice = scanner.nextInt();

            switch (choice) {

                case 1:
                    // Open a checking account
                    System.out.println("Enter first name:");
                    String firstName = scanner.next();
                    System.out.println("Enter last name:");
                    String lastName = scanner.next();
                    System.out.println("Enter social security number:");
                    String ssn = scanner.next();
                    System.out.println("Enter overdraft limit:");
                    double overdraftLimit = scanner.nextDouble();
                    scanner.nextLine();

                    Account checkingAccount = Bank.openAccount(firstName, lastName, ssn, "Checking");
                    System.out.println("Thank you, the account number is " + checkingAccount.getAccountNumber());
                    break;

                case 2:
                    // Open a saving account
                    System.out.println("Enter first name:");
                    String firstNameS = scanner.next();
                    System.out.println("Enter last name:");
                    String lastNameS = scanner.next();
                    System.out.println("Enter social security number:");
                    String ssnS = scanner.next();

                    Account savingAccount = Bank.openAccount(firstNameS, lastNameS, ssnS, "saving");
                    System.out.println("Thank you, the account number is " + savingAccount.getAccountNumber());
                    break;

                case 3:
                    // List account information
                    System.out.print("Enter account number: ");
                    int accNum = scanner.nextInt();

                    Account acc = Bank.findAccount(accNum);

                    if (acc == null) {
                        System.out.println("Account not found");
                    } else {
                        System.out.println(acc.toString() + " : " + acc.getBalance() + " : " + acc.getAccountStatus());
                    }
                    break;

                case 4:
                    // Print account statement
                    System.out.println("Enter account number:");
                    int accNumber = scanner.nextInt();

                    Bank.AccountTransactions(accNumber);
                    break;

                case 5:
                    // Deposit funds
                    try {
                        System.out.println("Enter account number:");
                        int accountNumber = scanner.nextInt();
                        System.out.println("Enter the amount to deposit:");
                        double amount = scanner.nextDouble();

                        Bank.deposit(accountNumber,amount);
                        System.out.println("Deposit successful, the new balance is: " + Bank.getBalance());
                    }
                    catch (NoSuchAccountException | AccountClosedException e) {
                        System.out.println(e.getMessage());
                    }
                    break;

                case 6:
                    // Withdraw funds
                   try {
                       System.out.println("Enter account number:");
                       int accountNO = scanner.nextInt();
                       System.out.println("Enter the withdrawal amount:");
                       double am = scanner.nextDouble();

                       Bank.withdraw(accountNO, am);
                       System.out.println("Withdrawal successful, the new balance is: " + Bank.getBalance());
                   }
                   catch (NoSuchAccountException | InsufficientBalanceException e){
                       System.out.println(e.getMessage());
                   }
                    break;

                case 7:
                    //Close an account
                    try {
                        System.out.println("Enter account number:");
                        int Number = scanner.nextInt();

                        Bank.closeAccount(Number);
                        System.out.println("Account closed, current balance is " + Bank.getBalance() + ", deposits are no longer possible.");

                    }catch (NoSuchAccountException e){
                        System.out.println(e.getMessage());
                    }

                    break;

                case 8:
                    // Write transactions to file
                    System.out.println("Enter account number:");
                    int accountNumber = scanner.nextInt();

                    String fileName = "transactions.txt";

                    try (FileOutputStream fos = new FileOutputStream(fileName)) {
                        Bank.printAccountTransactions(accountNumber, fos);
                        System.out.println("Transactions written to file: " + fileName);
                    } catch (NoSuchAccountException e) {
                        System.out.println("Error: " + e.getMessage());
                    } catch (IOException e) {
                        System.out.println("Error writing transactions to file: " + e.getMessage());
                    }
                    break;

                case 9:
                    System.out.println("Exit!");
                    System.exit(0);
                    break;

                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }
        }
    }


}