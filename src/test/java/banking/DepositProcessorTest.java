package banking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DepositProcessorTest {
    public Bank bank;
    public DepositProcessor depositProcessor;

    @BeforeEach
    public void setUp() {
        this.bank = new Bank();
        this.bank.createAccount(12345678, .1, "checking");
        this.bank.createAccount(23456789, .1, "savings");
        this.depositProcessor = new DepositProcessor(this.bank);
    }

    @Test
    public void deposit_checking() {
        this.depositProcessor.process("deposit 12345678 100");
        assertEquals(this.bank.accounts.get(12345678).getAmount(), 100);
    }

    @Test
    public void deposit_checking_case_insensitive() {
        this.depositProcessor.process("DePoSiT 12345678 100");
        assertEquals(this.bank.accounts.get(12345678).getAmount(), 100);
    }

    @Test
    public void deposit_checking_with_decimals() {
        this.bank.depositIntoAccount(12345678, 100);
        this.depositProcessor.process("deposit 12345678 100.1");
        assertEquals(this.bank.accounts.get(12345678).getAmount(), 200.1, 0.00001);
    }

    @Test
    public void deposit_savings() {
        this.depositProcessor.process("deposit 23456789 100");
        assertEquals(this.bank.accounts.get(23456789).getAmount(), 100);
    }
}
