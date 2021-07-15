import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AccountTest {
    public Account checkingAccount;
    public Account savingsAccount;
    public Account cdAccount;

    @BeforeEach
    public void setUp() {
        this.checkingAccount = new CheckingAccount(.1);
        this.savingsAccount = new SavingsAccount(.1);
        this.cdAccount = new CDAccount(100, .1);
    }

    @Test
    public void default_checking_account_balance() {
        assertEquals(this.checkingAccount.getAmount(), 0);
    }

    @Test
    public void default_savings_account_balance() {
        assertEquals(this.savingsAccount.getAmount(), 0);
    }

    @Test
    public void default_cd_account_balance() {
        assertEquals(this.cdAccount.getAmount(), 100);
    }

    @Test
    public void deposit() {
        this.checkingAccount.deposit(100);
        assertEquals(this.checkingAccount.getAmount(), 100);
    }

    @Test
    public void deposit_twice() {
        this.checkingAccount.deposit(100);
        this.checkingAccount.deposit(10);
        assertEquals(this.checkingAccount.getAmount(), 110);
    }

    @Test
    public void withdraw() {
        this.savingsAccount.deposit(100);
        this.savingsAccount.withdraw(70);
        assertEquals(this.savingsAccount.getAmount(), 30);
    }

    @Test
    public void withdraw_more_than_balance() {
        this.checkingAccount.deposit(100);
        this.checkingAccount.withdraw(150);
        assertEquals(this.checkingAccount.getAmount(), 0);
    }

    @Test
    public void withdraw_twice() {
        this.checkingAccount.deposit(100);
        this.checkingAccount.withdraw(50);
        this.checkingAccount.withdraw(20);
        assertEquals(this.checkingAccount.getAmount(), 30);
    }
}
