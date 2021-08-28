package banking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PassTimeProcessorTest {
    public Bank bank;
    public PassTimeProcessor passTimeProcessor;

    @BeforeEach
    public void setUp() {
        this.bank = new Bank();
        this.bank.createCheckingAccount(12345678, 2);
        this.bank.createSavingsAccount(23456789, 3);
        this.bank.createCDAccount(34567890, 5000, 1);
        this.passTimeProcessor = new PassTimeProcessor(bank);
    }

    @Test
    public void pass_time_empty_accounts() {
        this.passTimeProcessor.process("pass 1");
        assertEquals(this.bank.accounts.size(), 1);
    }

    @Test
    public void pass_time_checking_account() {
        this.bank.accounts.get(12345678).deposit(300);
        this.passTimeProcessor.process("pass 1");
        assertEquals(this.bank.accounts.get(12345678).getAmount(), 300.5, 0.01);
    }

    @Test
    public void pass_time_savings_account() {
        this.bank.accounts.get(23456789).deposit(100);
        this.passTimeProcessor.process("pass 1");
        assertEquals(this.bank.accounts.get(23456789).getAmount(), 100.25, 0.01);
    }

    @Test
    public void pass_time_CD_account() {
        this.passTimeProcessor.process("pass 1");
        assertEquals(this.bank.accounts.get(34567890).getAmount(), 5016.68, 0.01);
    }

    @Test
    public void pass_time_leads_to_no_balance() {
        this.bank.accounts.get(12345678).deposit(25);
        this.passTimeProcessor.process("pass 1");
        assertEquals(this.bank.accounts.get(12345678).getAmount(), 0);
    }

    @Test
    public void pass_time_leading_to_deleting_account() {
        this.bank.accounts.get(12345678).deposit(90);
        this.passTimeProcessor.process("pass 5");
        assertEquals(this.bank.accounts.size(), 1);
    }
}
