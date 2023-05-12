//Aileen Dong (ydong8@toromail.csudh.edu)

package Assignment5;
public class Transaction {

    static String CREDIT="CREDIT";
    static String DEBIT="DEBIT";
    int id;
    static int idFrom=1;
    double amount;

    int accountNumber;

    String type;


    public Transaction(String type, double amount,int accountNumber) {
        this.id=idFrom++;
        this.type = type;
        this.amount = amount;
        this.accountNumber=accountNumber;
    }

    public int getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public double getAmount() {
        return amount;
    }

    public String toString() {
        return (this.id+":"+(type==this.CREDIT?"Deposit":"Withdrawal")+"\t: "+amount);
    }
}
