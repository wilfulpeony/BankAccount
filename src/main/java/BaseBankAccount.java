import java.util.UUID;

public abstract class BaseBankAccount {

    public UUID idNumber;
    public double balance;
    public String ownerID;

    protected String accountType;

    public void openAccount(double balance, String ownerId) {
        this.balance = balance;
        ownerID = ownerId;
    }

    public void displayBalance(String ownerName) {

    }

    public abstract void depositFunds(double amount);

    public abstract void withdrawFunds(double amount);

    public abstract void setOverdraft(double limit);

}
