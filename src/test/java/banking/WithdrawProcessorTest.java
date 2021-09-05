package banking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class WithdrawProcessorTest {
    public Bank bank;
    public WithdrawProcessor withdrawProcessor;

    @BeforeEach
    public void setUp() {
        this.bank = new Bank();
        this.bank.createAccount(12345678, 0.1, "checking");
        this.bank.createAccount(23456789, 0.1, "savings");
        this.bank.createAccount(34567890, 2000, 1);
        this.bank.depositIntoAccount(12345678, 300);
        this.bank.depositIntoAccount(23456789, 900);
        this.withdrawProcessor = new WithdrawProcessor(this.bank);
    }

    @Test
    public void withdraw_checking() {
        this.withdrawProcessor.process("withdraw 12345678 100");
        assertEquals(this.bank.accounts.get(12345678).getAmount(), 200);
    }

    @Test
    public void withdraw_checking_case_insensitive() {
        this.withdrawProcessor.process("WiThDrAw 12345678 100");
        assertEquals(this.bank.accounts.get(12345678).getAmount(), 200);
    }

    @Test
    public void withdraw_checking_with_decimals() {
        this.withdrawProcessor.process("withdraw 12345678 100.1");
        assertEquals(this.bank.accounts.get(12345678).getAmount(), 199.9, 0.00001);
    }

    @Test
    public void withdraw_over_balance() {
        this.withdrawProcessor.process("withdraw 12345678 400");
        assertEquals(this.bank.accounts.get(12345678).getAmount(), 0);
    }

    @Test
    public void withdraw_savings() {
        this.withdrawProcessor.process("withdraw 23456789 100");
        assertEquals(this.bank.accounts.get(23456789).getAmount(), 800);
    }
}
