import java.util.UUID;

public class AccountWithOverdraft extends BaseBankAccount implements IProceedOverdraft {

    public double overdraftLimit = 800;
    public double maxOverdraftLimit = 800;

    public AccountWithOverdraft() {
        accountType = "Overdraft";
    }

    public String getAccountType() {
        return accountType;
    }

    public void setOverdraft(double limit) {
        if (limit < 0) {
            throw new RuntimeException("Not possible to do this operation");
        }
        if (maxOverdraftLimit == 0 && maxOverdraftLimit == overdraftLimit) {
            this.maxOverdraftLimit = limit;
            this.overdraftLimit = maxOverdraftLimit;
        } else if (maxOverdraftLimit > limit) {
            if (overdraftLimit > limit) {
                overdraftLimit = limit;
            }
            maxOverdraftLimit = limit;
        }
    }

    @Override
    public void withdrawFromOverDraft(double amount) {
        if (amount > overdraftLimit) {
            throw new RuntimeException("Overdraft limit is not enough");
        } else {
            overdraftLimit -= amount;
        }
    }

    @Override
    public void putOnOverDraftBalance(double amount) {
        if (amount < 0) {
            throw new RuntimeException("Not possible to do this operation");
        }
        this.overdraftLimit += amount;
    }

    @Override
    public void depositFunds(double amount) {
        if (amount < 0) {
            throw new RuntimeException("Not possible to do this operation");
        }
        if (maxOverdraftLimit > overdraftLimit) {
            double owed = maxOverdraftLimit - overdraftLimit;
            if (amount < owed) {
                putOnOverDraftBalance(amount);
            } else {
                putOnOverDraftBalance(owed);
                balance += owed - amount;
            }
        } else {
            balance += amount;
        }
    }

    @Override
    public void withdrawFunds(double amount) {
        if (amount < 0) {
            throw new RuntimeException("Not possible to do this operation");
        }
        if (amount > balance) {
            double amountFromOverDraft = amount - balance;
            withdrawFromOverDraft(amountFromOverDraft);
            balance = 0;
        } else {
            balance -= amount;
        }
    }
}
