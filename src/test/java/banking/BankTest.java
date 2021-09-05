package banking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class BankTest {
    public Bank bank;

    @BeforeEach
    public void setUp() {
        this.bank = new Bank();
        this.bank.createAccount(12345678, .1, "checking");
        this.bank.createAccount(23456789, .1, "savings");
        this.bank.createAccount(34567890, 100, .1);
    }

    @Test
    public void empty_bank() {
        this.bank.accounts.clear();
        this.bank.accountHistory.clear();
        assertEquals(this.bank.accounts.size(), 0);
    }

    @Test
    public void create_checking_account() {
        this.bank.accounts.clear();
        this.bank.accountHistory.clear();
        this.bank.createAccount(12345678, .1, "checking");
        assertEquals(this.bank.accounts.size(), 1);
        assertEquals(this.bank.accountHistory.size(), 1);
    }

    @Test
    public void create_savings_account() {
        this.bank.accounts.clear();
        this.bank.accountHistory.clear();
        this.bank.createAccount(23456789, .1, "savings");
        assertEquals(this.bank.accounts.size(), 1);
        assertEquals(this.bank.accountHistory.size(), 1);
    }

    @Test
    public void create_cd_account() {
        this.bank.accounts.clear();
        this.bank.accountHistory.clear();
        this.bank.createAccount(34567890, 100, .1);
        assertEquals(this.bank.accounts.size(), 1);
        assertEquals(this.bank.accountHistory.size(), 1);
    }

    @Test
    public void create_multiple() {
        assertEquals(this.bank.accounts.size(), 3);
    }

    @Test
    public void deposit_into_account() {
        this.bank.depositIntoAccount(23456789, 100);
        assertEquals(this.bank.accounts.get(23456789).getAmount(), 100);
    }

    @Test
    public void deposit_into_account_with_history() {
        this.bank.depositIntoAccount(23456789, 100, "Deposit 23456789 100");
        assertEquals(this.bank.accounts.get(23456789).getAmount(), 100);
        assertEquals(this.bank.accountHistory.get(23456789).get(0), "Deposit 23456789 100");
    }

    @Test
    public void withdraw_from_account() {
        this.bank.depositIntoAccount(12345678, 100);
        this.bank.withdrawFromAccount(12345678, 70);
        assertEquals(this.bank.accounts.get(12345678).getAmount(), 30);
    }

    @Test
    public void withdraw_from_account_with_history() {
        this.bank.depositIntoAccount(12345678, 100, "Deposit 12345678 100");
        this.bank.withdrawFromAccount(12345678, 70, "Withdraw 12345678 70");
        assertEquals(this.bank.accounts.get(12345678).getAmount(), 30);
        assertEquals(this.bank.accountHistory.get(12345678).get(1), "Withdraw 12345678 70");
    }

    @Test
    public void transfer_between_checking_and_savings() {
        this.bank.depositIntoAccount(12345678, 100);
        this.bank.transfer(12345678, 23456789, 50);
        assertEquals(this.bank.accounts.get(12345678).getAmount(), 50);
        assertEquals(this.bank.accounts.get(23456789).getAmount(), 50);
    }

    @Test
    public void transfer_between_checking_and_savings_with_history() {
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
        this.bank.depositIntoAccount(12345678, 100);
        this.bank.transfer(12345678, 23456789, 101);
        assertEquals(this.bank.accounts.get(12345678).getAmount(), 0);
        assertEquals(this.bank.accounts.get(23456789).getAmount(), 100);
    }

    @Test
    public void full_transfer_leads_to_0() {
        this.bank.depositIntoAccount(12345678, 100);
        this.bank.transfer(12345678, 23456789, this.bank.accounts.get(12345678).getAmount());
        assertEquals(this.bank.accounts.get(12345678).getAmount(), 0);
        assertEquals(this.bank.accounts.get(23456789).getAmount(), 100);
    }

    @Test
    public void get_state_checking() {
        assertEquals(this.bank.getStateOfAccount(12345678), "Checking 12345678 0.00 0.10");
    }

    @Test
    public void get_state_savings() {
        assertEquals(this.bank.getStateOfAccount(23456789), "Savings 23456789 0.00 0.10");
    }

    @Test
    public void get_state_cd() {
        assertEquals(this.bank.getStateOfAccount(34567890), "Cd 34567890 100.00 0.10");
    }

    @Test
    public void deposit_into_checking_below_1000() {
        assertTrue(this.bank.accountDepositUnderLimit(1000, 12345678));
    }

    @Test
    public void deposit_into_savings_below_2500() {
        assertTrue(this.bank.accountDepositUnderLimit(2500, 23456789));
    }

    @Test
    public void withdraw_from_checking_below_400() {
        this.bank.depositIntoAccount(12345678, 400);
        assertTrue(this.bank.accountWithdrawUnderLimit(400, 12345678));
    }

    @Test
    public void withdraw_from_savings_below_1000() {
        this.bank.depositIntoAccount(23456789, 1000);
        assertTrue(this.bank.accountWithdrawUnderLimit(1000, 23456789));
    }

    @Test
    public void checking_pass_time() {
        this.bank.accounts.get(12345678).deposit(100);
        this.bank.passTime(12);
        assertTrue(this.bank.accountWithdrawUnderLimit(100, 12345678));
    }

    @Test
    public void savings_pass_time() {
        this.bank.accounts.get(23456789).deposit(100);
        this.bank.passTime(12);
        assertTrue(this.bank.accountWithdrawUnderLimit(100, 23456789));
    }

    @Test
    public void withdraw_from_cd_after_12() {
        this.bank.passTime(12);
        assertTrue(this.bank.accountWithdrawUnderLimit(this.bank.accounts.get(34567890).getAmount(), 34567890));
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
        this.bank.depositIntoAccount(23456789, 1000);
        assertTrue(this.bank.validTransfer(23456789, 12345678, 1000));
    }

    @Test
    public void deposit_into_checking_above_1000() {
        assertFalse(this.bank.accountDepositUnderLimit(1001, 12345678));
    }

    @Test
    public void deposit_into_savings_above_2500() {
        assertFalse(this.bank.accountDepositUnderLimit(2501, 23456789));
    }

    @Test
    public void deposit_into_cd_not_possible() {
        assertFalse(this.bank.accountDepositUnderLimit(100, 34567890));
    }

    @Test
    public void withdraw_from_checking_above_400() {
        this.bank.depositIntoAccount(12345678, 401);
        assertFalse(this.bank.accountWithdrawUnderLimit(401, 12345678));
    }

    @Test
    public void withdraw_from_savings_above_1000() {
        this.bank.depositIntoAccount(23456789, 1001);
        assertFalse(this.bank.accountWithdrawUnderLimit(1001, 23456789));
    }

    @Test
    public void withdraw_from_cd_too_early() {
        assertFalse(this.bank.accountWithdrawUnderLimit(100, 34567890));
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
        this.bank.depositIntoAccount(12345678, 1000);
        assertFalse(this.bank.validTransfer(12345678, 23456789, 1000));
    }
}
