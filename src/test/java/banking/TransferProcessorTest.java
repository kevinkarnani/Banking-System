package banking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TransferProcessorTest {
    public Bank bank;
    public TransferProcessor transferProcessor;

    @BeforeEach
    public void setUp() {
        this.bank = new Bank();
        this.bank.createAccount(12345678, 1, "checking");
        this.bank.createAccount(23456789, 1, "savings");
        this.bank.depositIntoAccount(12345678, 100);
        this.bank.depositIntoAccount(23456789, 100);
        this.transferProcessor = new TransferProcessor(this.bank);
    }

    @Test
    public void transfer_between_savings_and_checking_accounts() {
        this.transferProcessor.process("transfer 12345678 23456789 50");
        assertEquals(this.bank.accounts.get(12345678).getAmount(), 50);
        assertEquals(this.bank.accounts.get(23456789).getAmount(), 150);
    }

    @Test
    public void transfer_between_checking_and_savings_accounts() {
        this.transferProcessor.process("transfer 23456789 12345678 50");
        assertEquals(this.bank.accounts.get(12345678).getAmount(), 150);
        assertEquals(this.bank.accounts.get(23456789).getAmount(), 50);
    }

    @Test
    public void transfer_over_balance_is_valid() {
        this.transferProcessor.process("transfer 12345678 23456789 300");
        assertEquals(this.bank.accounts.get(12345678).getAmount(), 0);
        assertEquals(this.bank.accounts.get(23456789).getAmount(), 200);
    }

    @Test
    public void transfer_case_insensitive() {
        this.transferProcessor.process("TrAnSfEr 12345678 23456789 300");
        assertEquals(this.bank.accounts.get(12345678).getAmount(), 0);
        assertEquals(this.bank.accounts.get(23456789).getAmount(), 200);
    }
}
