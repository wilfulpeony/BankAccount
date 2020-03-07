import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class BankAccountService {

    public static List<BaseBankAccount> accountRepository = new ArrayList<>();

    static {
        BaseBankAccount acc = new AccountWithoutOverdraft();
        acc.ownerID = "basic";
        acc.balance = 123;
        accountRepository.add(acc);
        acc = new AccountWithOverdraft();
        acc.ownerID = "overdraft";
        acc.balance = 123;
        accountRepository.add(acc);

    }

    public BaseBankAccount openBankAccount(String type, double balance, String ownerId) {
        if (balance < 0 || balance > 100000000) {
            throw new RuntimeException();
        }
        if (type.toLowerCase().contains("overdraft")) {
            BaseBankAccount account = new AccountWithOverdraft();
            account.setOverdraft(1000);
            BaseBankAccount accountValue;
            try {
                accountValue = new BankAccountService().getBankAccount(ownerId);
            } catch (Exception ex) {
                account.openAccount(balance, ownerId);
                accountRepository.add(account);
                return account;
            }
            return null;
        } else {
            BaseBankAccount account = new AccountWithoutOverdraft();
            BaseBankAccount accountValue;
            try {
                accountValue = new BankAccountService().getBankAccount(ownerId);
            } catch (Exception ex) {
                account.openAccount(balance, ownerId);
                accountRepository.add(account);
                return account;
            }
            return null;
        }

    }

    public BaseBankAccount withdrawMoney(String id, double amount) {
        List<BaseBankAccount> tempAccountList = accountRepository.stream().filter(p -> p.ownerID.toString().equals(id)).collect(Collectors.toList());
        if (tempAccountList.size() == 0) {
            throw new RuntimeException("No such account");
        } else {
            tempAccountList.get(0).withdrawFunds(amount);
            return tempAccountList.get(0);
        }

    }

    public BaseBankAccount putMoneyOnAccount(String id, double amount) {
        List<BaseBankAccount> tempAccountList = accountRepository.stream().filter(p -> p.ownerID.toString().equals(id)).collect(Collectors.toList());
        if (tempAccountList.size() == 0) {
            throw new RuntimeException("No such account");
        } else {
            tempAccountList.get(0).depositFunds(amount);
            return tempAccountList.get(0);
        }
    }

    public BaseBankAccount getBankAccount(String ownerId) {
        List<BaseBankAccount> tempAccountList = accountRepository.stream().filter(p -> p.ownerID.toString().equals(ownerId)).collect(Collectors.toList());
        if (tempAccountList.size() == 0) {
            throw new RuntimeException("No such account");
        } else {
            return tempAccountList.get(0);
        }
    }
}