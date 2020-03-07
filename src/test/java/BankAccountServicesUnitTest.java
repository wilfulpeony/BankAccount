
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;


import java.util.ArrayList;

public class BankAccountServicesUnitTest {

    @ParameterizedTest
    @ValueSource(strings = {"Basic", "Overdraft"})
    public void  verifyUserCanCreateAccountWithValidData(String type){
        BankAccountService service = new BankAccountService();
        service.openBankAccount(type,1000, "Mister Smith");
        BaseBankAccount account = BankAccountService.accountRepository.stream().filter(p->p.ownerID.equals("Mister Smith")).findFirst().get();
        Assertions.assertEquals(account.accountType, type);
        Assertions.assertEquals(Double.valueOf(account.balance), Double.valueOf(1000));
    }

    @ParameterizedTest
    @ValueSource(strings = {"Basic", "Overdraft"})
    public void  verifyUniqueAccountCreation(String type){
        BankAccountService service = new BankAccountService();
        BaseBankAccount account = service.openBankAccount("Type",1000, "Mister Test");
        account = service.openBankAccount(type,1000, "Mister Test");
        Assertions.assertEquals(null, account);
    }
    @Test
    public void verifyInvalidInputsForOpeningAccounts(){
        BankAccountService service = new BankAccountService();
        BaseBankAccount account = service.openBankAccount("Basic", 100,null);
        Assertions.assertEquals(null, account.ownerID);
    }

    @ParameterizedTest
    @ValueSource(strings = {"Basic", "Overdraft"})
    public void verifyWithdrawServiceWithValidData(String type){
        BankAccountService service = new BankAccountService();
        service.openBankAccount(type,1000, "Mister Smith");
        service.withdrawMoney("Mister Smith", 100);
        BaseBankAccount account = BankAccountService.accountRepository.stream().filter(p->p.ownerID.equals("Mister Smith")).findFirst().get();
        Assertions.assertEquals(900, account.balance);
    }
    @ParameterizedTest
    @ValueSource(strings = { "Overdraft"})
    public void verifyWithdrawForOverdraftAccountPartOne(String type){
        //limit is -800
        BankAccountService service = new BankAccountService();
        service.openBankAccount(type,1000, "Mister Smith");
        service.withdrawMoney("Mister Smith", 1200);
        double overdraft = ((AccountWithOverdraft)service.getBankAccount("Mister Smith")).overdraftLimit;
        BaseBankAccount account = BankAccountService.accountRepository.stream().filter(p->p.ownerID.equals("Mister Smith")).findFirst().get();
        Assertions.assertEquals(600, overdraft);
    }
    @ParameterizedTest
    @ValueSource(strings = { "Overdraft"})
    public void verifyWithdrawForOverdraftAccountPartTwo(String type){
        //limit is -800
        BankAccountService service = new BankAccountService();
        service.openBankAccount(type,1000, "Mister Smith");
        service.withdrawMoney("Mister Smith", 1800);
        double overdraft = ((AccountWithOverdraft)service.getBankAccount("Mister Smith")).overdraftLimit;
        BaseBankAccount account = BankAccountService.accountRepository.stream().filter(p->p.ownerID.equals("Mister Smith")).findFirst().get();
        Assertions.assertEquals(0, overdraft);
    }

    @ParameterizedTest
    @ValueSource(strings = { "Overdraft"})
    public void verifyWithdrawForOverdraftAccountPartThree(String type){
        //limit is -800
        BankAccountService service = new BankAccountService();
        service.openBankAccount(type,1000, "Mister Smith");
        service.withdrawMoney("Mister Smith", 1799);
        double overdraft = ((AccountWithOverdraft)service.getBankAccount("Mister Smith")).overdraftLimit;
        BaseBankAccount account = BankAccountService.accountRepository.stream().filter(p->p.ownerID.equals("Mister Smith")).findFirst().get();
        Assertions.assertEquals(1, overdraft);
    }
    @ParameterizedTest
    @ValueSource(strings = { "Overdraft"})
    public void verifyWithdrawForOverdraftAccountPartFour(String type){
        //limit is -800

        BankAccountService service = new BankAccountService();
        service.openBankAccount(type,1000, "Mister Smith");
        try{
        service.withdrawMoney("Mister Smith", 1801);
        double overdraft = ((AccountWithOverdraft)service.getBankAccount("Mister Smith")).overdraftLimit;
        BaseBankAccount account = BankAccountService.accountRepository.stream().filter(p->p.ownerID.equals("Mister Smith")).findFirst().get();}
        catch ( Exception ex){
            Assertions.assertEquals("Overdraft limit is not enough", ex.getMessage());
        }

    }

    @ParameterizedTest
    @ValueSource(strings = { "Basic"})
    public void verifyWithdrawForNonOverdraftAccountPartOne(String type){


        BankAccountService service = new BankAccountService();
        service.openBankAccount(type,1000, "Mister Smith");
        service.withdrawMoney("Mister Smith", 100);

        BaseBankAccount account = BankAccountService.accountRepository.stream().filter(p->p.ownerID.equals("Mister Smith")).findFirst().get();
        Assertions.assertEquals(900, account.balance);

    }
    @ParameterizedTest
    @ValueSource(strings = { "Basic"})
    public void verifyWithdrawForNonOverdraftAccountPartTwo(String type){


        BankAccountService service = new BankAccountService();
        service.openBankAccount(type,1000, "Mister Smith");
        service.withdrawMoney("Mister Smith", 999);

        BaseBankAccount account = BankAccountService.accountRepository.stream().filter(p->p.ownerID.equals("Mister Smith")).findFirst().get();
        Assertions.assertEquals(1, account.balance);

    }

    @ParameterizedTest
    @ValueSource(strings = { "Basic"})
    public void verifyWithdrawForNonOverdraftAccountPartThree(String type){


        BankAccountService service = new BankAccountService();
        service.openBankAccount(type,1000, "Mister Smith");
        service.withdrawMoney("Mister Smith", 1);

        BaseBankAccount account = BankAccountService.accountRepository.stream().filter(p->p.ownerID.equals("Mister Smith")).findFirst().get();
        Assertions.assertEquals(999, account.balance);

    }

    @ParameterizedTest
    @ValueSource(strings = { "Basic"})
    public void verifyWithdrawForNonOverdraftAccountPartFour(String type){


        BankAccountService service = new BankAccountService();
        service.openBankAccount(type,1000, "Mister Smith");
        service.withdrawMoney("Mister Smith", 0);

        BaseBankAccount account = BankAccountService.accountRepository.stream().filter(p->p.ownerID.equals("Mister Smith")).findFirst().get();
        Assertions.assertEquals(1000, account.balance);

    }

    @ParameterizedTest
    @ValueSource(strings = { "Basic"})
    public void verifyWithdrawForNonOverdraftAccountPartFive(String type){
        BankAccountService service = new BankAccountService();
        service.openBankAccount(type,1000, "Mister Smith");
        try{
        service.withdrawMoney("Mister Smith", 1001);

        BaseBankAccount account = BankAccountService.accountRepository.stream().filter(p->p.ownerID.equals("Mister Smith")).findFirst().get();}
        catch (Exception ex){
        Assertions.assertEquals("Not possible to do this operation", ex.getMessage());}

    }

    @ParameterizedTest
    @ValueSource(doubles = {1, 100, 1000, 10.5, 0.5,0.01})
    public void  verifyUserCanPutMoneyOnBasicAccount(double amount){
        BankAccountService service = new BankAccountService();
        service.openBankAccount("Basic",0, "Mister Smith");
        service.putMoneyOnAccount("Mister Smith", amount);
        BaseBankAccount account = BankAccountService.accountRepository.stream().filter(p->p.ownerID.equals("Mister Smith")).findFirst().get();

        Assertions.assertEquals(Double.valueOf(account.balance), amount);
    }

    @ParameterizedTest
    @ValueSource(doubles = {1, 100, 1000, 10.5, 0.5,0.01})
    public void  verifyUserCanPutMoneyOnOverdraftAccount(double amount){
        BankAccountService service = new BankAccountService();
        service.openBankAccount("Overdraft",0, "Mister Smith");
        service.putMoneyOnAccount("Mister Smith", amount);
        BaseBankAccount account = BankAccountService.accountRepository.stream().filter(p->p.ownerID.equals("Mister Smith")).findFirst().get();

        Assertions.assertEquals(Double.valueOf(account.balance), amount);
    }

    @ParameterizedTest
    @ValueSource(strings = {"Basic", "Overdraft"})
    public void  verifyUserCanGetAccountInfo(String type){
        BankAccountService service = new BankAccountService();
        service.openBankAccount(type,0, "Mister Smith");
        service.getBankAccount("Mister Smith");
        BaseBankAccount account = BankAccountService.accountRepository.stream().filter(p->p.ownerID.equals("Mister Smith")).findFirst().get();

        Assertions.assertEquals("Mister Smith", account.ownerID);
    }
    @ParameterizedTest
    @ValueSource(strings = {"Basic", "Overdraft"})
    public void  verifyUserCantGetUnvalidAccountInfo(String type){
        BankAccountService service = new BankAccountService();
        try{
        service.getBankAccount("Test Account");}
        catch (Exception ex){

        Assertions.assertEquals("No such account", ex.getMessage());}
    }
    @AfterEach
    public void remove() {
        BankAccountService.accountRepository = new ArrayList<>();
    }
}
