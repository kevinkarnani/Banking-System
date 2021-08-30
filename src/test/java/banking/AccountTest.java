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
    public void checking_max_withdraw_is_400() {
        assertTrue(this.checkingAccount.validWithdrawAmount(400));
    }

    @Test
    public void savings_max_withdraw_is_1000() {
        assertTrue(this.savingsAccount.validWithdrawAmount(1000));
    }

    @Test
    public void withdraw_all_from_cd_after_12_months() {
        this.cdAccount.months = 12;
        assertTrue(this.cdAccount.validWithdrawAmount(this.cdAccount.getAmount()));
    }

    @Test
    public void withdraw_more_than_balance_from_cd_after_12_months() {
        this.cdAccount.months = 12;
        assertTrue(this.cdAccount.validWithdrawAmount(this.cdAccount.getAmount() * 2));
    }

    @Test
    public void transfer_limit_for_checking_is_400() {
        assertTrue(this.checkingAccount.validTransferAmount(400));
    }

    @Test
    public void transfer_limit_for_savings_is_1000() {
        assertTrue(this.savingsAccount.validTransferAmount(1000));
    }

    @Test
    public void checking_over_max_deposit_is_invalid() {
        assertFalse(this.checkingAccount.validDepositAmount(10000));
    }

    @Test
    public void savings_over_max_deposit_is_invalid() {
        assertFalse(this.checkingAccount.validDepositAmount(10000));
    }

    @Test
    public void checking_over_max_withdraw_is_invalid() {
        assertFalse(this.checkingAccount.validWithdrawAmount(10000));
    }

    @Test
    public void savings_over_max_withdraw_is_invalid() {
        assertFalse(this.checkingAccount.validWithdrawAmount(10000));
    }

    @Test
    public void withdraw_half_from_cd_after_12_months_is_invalid() {
        this.cdAccount.months = 12;
        assertFalse(this.cdAccount.validWithdrawAmount(this.cdAccount.getAmount() / 2));
    }

    @Test
    public void withdraw_before_12_months_is_invalid() {
        this.cdAccount.months = 11;
        assertFalse(this.cdAccount.validWithdrawAmount(this.cdAccount.getAmount()));
    }

    @Test
    public void transfer_checking_above_400() {
        assertFalse(this.checkingAccount.validTransferAmount(2000));
    }

    @Test
    public void transfer_savings_above_1000() {
        assertFalse(this.savingsAccount.validTransferAmount(2000));
    }

    @Test
    public void cannot_transfer_from_CD() {
        assertFalse(this.cdAccount.validTransferAmount(this.cdAccount.getAmount()));
    }
}
