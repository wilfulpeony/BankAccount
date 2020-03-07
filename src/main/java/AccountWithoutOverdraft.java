import java.util.UUID;

public class AccountWithoutOverdraft extends BaseBankAccount {

    public AccountWithoutOverdraft() {
        accountType = "Basic";
    }

    public String getAccountType() {
        return accountType;
    }


    @Override
    public void depositFunds(double amount) {
        if (amount < 0) {
            throw new RuntimeException("Not possible to do this operation");
        }
        balance += amount;
    }


    @Override
    public void withdrawFunds(double amount) {
        if (amount < 0 || amount > balance) {
            throw new RuntimeException("Not possible to do this operation");
        }

        balance -= amount;
    }

    @Override
    public void setOverdraft(double limit) {
        throw new RuntimeException("This account type does not support overdraft");
    }
}
