package banking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

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
    public void deposit_1000_into_checking_is_valid() {
        this.checkingAccount.deposit(1000);
        assertEquals(this.checkingAccount.getAmount(), 1000);
    }

    @Test
    public void deposit_2500_into_savings_is_valid() {
        this.savingsAccount.deposit(2500);
        assertEquals(this.savingsAccount.getAmount(), 2500);
    }

    @Test
    public void withdraw() {
        this.savingsAccount.deposit(100);
        this.savingsAccount.withdraw(70);
        assertEquals(this.savingsAccount.getAmount(), 30);
    }

    @Test
    public void withdraw_zero_is_valid() {
        this.checkingAccount.withdraw(0);
        assertEquals(this.checkingAccount.getAmount(), 0);
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

    @Test
    public void withdraw_below_0_ends_in_0() {
        this.checkingAccount.withdraw(100);
        assertEquals(this.checkingAccount.getAmount(), 0);
    }

    @Test
    public void withdraw_0_is_valid() {
        this.checkingAccount.withdraw(0);
        assertEquals(this.checkingAccount.getAmount(), 0);
    }

    @Test
    public void checking_max_deposit_is_1000() {
        assertTrue(this.checkingAccount.validDepositAmount(1000));
    }

    @Test
    public void savings_max_deposit_is_2500() {
        assertTrue(this.savingsAccount.validDepositAmount(2500));
    }

    @Test
    public void checking_over_max_deposit_is_invalid() {
        assertFalse(this.checkingAccount.validDepositAmount(10000));
    }

    @Test
    public void savings_over_max_deposit_is_invalid() {
        assertFalse(this.checkingAccount.validDepositAmount(10000));
    }
}
