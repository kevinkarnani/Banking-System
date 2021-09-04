package banking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class BankTest {
    public Bank bank;

    @BeforeEach
    public void setUp() {
        this.bank = new Bank();
    }

    @Test
    public void empty_bank() {
        assertEquals(this.bank.accounts.size(), 0);
    }

    @Test
    public void create_checking_account() {
        this.bank.createCheckingAccount(12345678, .1);
        assertEquals(this.bank.accounts.size(), 1);
        assertEquals(this.bank.accountHistory.size(), 1);
    }

    @Test
    public void create_savings_account() {
        this.bank.createSavingsAccount(12345678, .1);
        assertEquals(this.bank.accounts.size(), 1);
        assertEquals(this.bank.accountHistory.size(), 1);
    }

    @Test
    public void create_cd_account() {
        this.bank.createCDAccount(12345678, 100, .1);
        assertEquals(this.bank.accounts.size(), 1);
        assertEquals(this.bank.accountHistory.size(), 1);
    }

    @Test
    public void create_multiple() {
        this.bank.createCheckingAccount(1234567, .1);
        this.bank.createCDAccount(12345679, 100, .1);
        this.bank.createSavingsAccount(123456780, .1);
        assertEquals(this.bank.accounts.size(), 3);
    }

    @Test
    public void deposit_into_account() {
        this.bank.createSavingsAccount(12345678, .1);
        this.bank.depositIntoAccount(12345678, 100);
        assertEquals(this.bank.accounts.get(12345678).getAmount(), 100);
    }

    @Test
    public void deposit_into_account_with_history() {
        this.bank.createSavingsAccount(12345678, .1);
        this.bank.depositIntoAccount(12345678, 100, "Deposit 12345678 100");
        assertEquals(this.bank.accounts.get(12345678).getAmount(), 100);
        assertEquals(this.bank.accountHistory.get(12345678).get(0), "Deposit 12345678 100");
    }

    @Test
    public void withdraw_from_account() {
        this.bank.createCheckingAccount(12345678, .1);
        this.bank.depositIntoAccount(12345678, 100);
        this.bank.withdrawFromAccount(12345678, 70);
        assertEquals(this.bank.accounts.get(12345678).getAmount(), 30);
    }

    @Test
    public void withdraw_from_account_with_history() {
        this.bank.createCheckingAccount(12345678, .1);
        this.bank.depositIntoAccount(12345678, 100, "Deposit 12345678 100");
        this.bank.withdrawFromAccount(12345678, 70, "Withdraw 12345678 70");
        assertEquals(this.bank.accounts.get(12345678).getAmount(), 30);
        assertEquals(this.bank.accountHistory.get(12345678).get(1), "Withdraw 12345678 70");
    }

    @Test
    public void transfer_between_checking_and_savings() {
        this.bank.createCheckingAccount(12345678, 1);
        this.bank.createSavingsAccount(23456789, 1);
        this.bank.depositIntoAccount(12345678, 100);
        this.bank.transfer(12345678, 23456789, 50);
        assertEquals(this.bank.accounts.get(12345678).getAmount(), 50);
        assertEquals(this.bank.accounts.get(23456789).getAmount(), 50);
    }

    @Test
    public void transfer_between_checking_and_savings_with_history() {
        this.bank.createCheckingAccount(12345678, 1);
        this.bank.createSavingsAccount(23456789, 1);
        this.bank.depositIntoAccount(12345678, 100, "Deposit 12345678 100");
        this.bank.transfer(12345678, 23456789, 50, "Transfer 12345678 23456789 50");
        assertEquals(this.bank.accounts.get(12345678).getAmount(), 50);
        assertEquals(this.bank.accounts.get(23456789).getAmount(), 50);
        assertEquals(this.bank.accountHistory.get(12345678).get(0), "Deposit 12345678 100");
        assertEquals(this.bank.accountHistory.get(12345678).get(1), "Transfer 12345678 23456789 50");
        assertEquals(this.bank.accountHistory.get(23456789).get(0), "Transfer 12345678 23456789 50");
    }

    @Test
    public void transfer_no_overdraft() {
        this.bank.createCheckingAccount(12345678, 1);
        this.bank.createSavingsAccount(23456789, 1);
        this.bank.depositIntoAccount(12345678, 100);
        this.bank.transfer(12345678, 23456789, 200);
        assertEquals(this.bank.accounts.get(12345678).getAmount(), 0);
        assertEquals(this.bank.accounts.get(23456789).getAmount(), 100);
    }

    @Test
    public void full_transfer_leads_to_0() {
        this.bank.createCheckingAccount(12345678, 1);
        this.bank.createSavingsAccount(23456789, 1);
        this.bank.depositIntoAccount(12345678, 100);
        this.bank.transfer(12345678, 23456789, this.bank.accounts.get(12345678).getAmount());
        assertEquals(this.bank.accounts.get(12345678).getAmount(), 0);
        assertEquals(this.bank.accounts.get(23456789).getAmount(), 100);
    }

    @Test
    public void deposit_into_checking_below_1000() {
        this.bank.createCheckingAccount(12345678, 1);
        assertTrue(this.bank.accountDepositUnderLimit(1000, 12345678));
    }

    @Test
    public void deposit_into_savings_below_2500() {
        this.bank.createSavingsAccount(12345678, 1);
        assertTrue(this.bank.accountDepositUnderLimit(2500, 12345678));
    }

    @Test
    public void withdraw_from_checking_below_400() {
        this.bank.createCheckingAccount(12345678, 1);
        this.bank.depositIntoAccount(12345678, 400);
        assertTrue(this.bank.accountWithdrawUnderLimit(400, 12345678));
    }

    @Test
    public void withdraw_from_savings_below_1000() {
        this.bank.createSavingsAccount(12345678, 1);
        this.bank.depositIntoAccount(12345678, 1000);
        assertTrue(this.bank.accountWithdrawUnderLimit(1000, 12345678));
    }

    @Test
    public void withdraw_from_cd_after_12() {
        this.bank.createCDAccount(12345678, 100, 0);
        this.bank.passTime(12);
        assertTrue(this.bank.accountWithdrawUnderLimit(100, 12345678));
    }

    @Test

    public void min_apr_is_0() {
        assertTrue(this.bank.validateInitialAPR(0));
    }

    @Test
    public void max_apr_is_10() {
        assertTrue(this.bank.validateInitialAPR(10));
    }

    @Test
    public void CD_initial_1000_is_valid() {
        assertTrue(this.bank.validateInitialCDAmount(1000));
    }

    @Test
    public void CD_initial_10000_is_valid() {
        assertTrue(this.bank.validateInitialCDAmount(10000));
    }

    @Test
    public void transfer_1000_from_savings_to_checking_is_valid() {
        this.bank.createCheckingAccount(12345678, 1);
        this.bank.createSavingsAccount(23456789, 1);
        this.bank.depositIntoAccount(23456789, 1000);
        assertTrue(this.bank.validTransfer(23456789, 12345678, 1000));
    }

    @Test
    public void deposit_into_checking_above_1000() {
        this.bank.createCheckingAccount(12345678, 1);
        assertFalse(this.bank.accountDepositUnderLimit(1001, 12345678));
    }

    @Test
    public void deposit_into_savings_above_2500() {
        this.bank.createSavingsAccount(12345678, 1);
        assertFalse(this.bank.accountDepositUnderLimit(2501, 12345678));
    }

    @Test
    public void deposit_into_cd_not_possible() {
        this.bank.createCDAccount(12345678, 100, 1);
        assertFalse(this.bank.accountDepositUnderLimit(100, 12345678));
    }

    @Test
    public void withdraw_from_checking_above_400() {
        this.bank.createCheckingAccount(12345678, 1);
        this.bank.depositIntoAccount(12345678, 401);
        assertFalse(this.bank.accountWithdrawUnderLimit(401, 12345678));
    }

    @Test
    public void withdraw_from_savings_above_1000() {
        this.bank.createSavingsAccount(12345678, 1);
        this.bank.depositIntoAccount(12345678, 1001);
        assertFalse(this.bank.accountWithdrawUnderLimit(1001, 12345678));
    }

    @Test
    public void withdraw_from_cd_too_early() {
        this.bank.createCDAccount(12345678, 100, 1);
        assertFalse(this.bank.accountWithdrawUnderLimit(100, 12345678));
    }

    @Test
    public void apr_below_0_is_invalid() {
        assertFalse(this.bank.validateInitialAPR(-1));
    }

    @Test
    public void apr_above_10_is_invalid() {
        assertFalse(this.bank.validateInitialAPR(11));
    }

    @Test
    public void CD_initial_below_1000_is_invalid() {
        assertFalse(this.bank.validateInitialCDAmount(100));
    }

    @Test
    public void CD_initial_above_10000_is_invalid() {
        assertFalse(this.bank.validateInitialCDAmount(10001));
    }

    @Test
    public void transfer_1000_from_checking_to_savings() {
        this.bank.createCheckingAccount(12345678, 1);
        this.bank.createSavingsAccount(23456789, 1);
        this.bank.depositIntoAccount(12345678, 1000);
        assertFalse(this.bank.validTransfer(12345678, 23456789, 1000));
    }
}
