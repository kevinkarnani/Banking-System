package banking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CreateProcessorTest {
    public Bank bank;
    public CreateProcessor createProcessor;

    @BeforeEach
    public void setUp() {
        this.bank = new Bank();
        this.createProcessor = new CreateProcessor(this.bank);
    }

    @Test
    public void create_savings_empty() {
        this.createProcessor.process("Create savings 12345678 0.6");
        assertTrue(this.bank.accountExists(12345678));
    }

    @Test
    public void create_savings_empty_case_insensitive() {
        this.createProcessor.process("CrEaTe SaViNgS 12345678 0.6");
        assertTrue(this.bank.accountExists(12345678));
    }

    @Test
    public void create_savings_empty_zero_not_appended() {
        this.createProcessor.process("CrEaTe SaViNgS 12345678 .6");
        assertTrue(this.bank.accountExists(12345678));
    }

    @Test
    public void create_checking_empty() {
        this.createProcessor.process("Create checking 12345678 0.01");
        assertTrue(this.bank.accountExists(12345678));
    }

    @Test
    public void create_cd_empty() {
        this.createProcessor.process("Create cd 12345678 1.2 2000");
        assertTrue(this.bank.accountExists(12345678));
    }

    @Test
    public void create_savings_one() {
        bank.createSavingsAccount(12345678, 0.1);
        this.createProcessor.process("Create savings 23456789 0.6");
        assertTrue(this.bank.accountExists(23456789));
        assertEquals(this.bank.accounts.size(), 2);
    }

    @Test
    public void create_checking_one() {
        bank.createCheckingAccount(12345678, 0.1);
        this.createProcessor.process("Create checking 23456789 0.01");
        assertTrue(this.bank.accountExists(23456789));
        assertEquals(this.bank.accounts.size(), 2);
    }

    @Test
    public void create_cd_one() {
        bank.createCDAccount(12345678, 2000, 0.1);
        this.createProcessor.process("Create cd 23456789 1.2 2000");
        assertTrue(this.bank.accountExists(23456789));
        assertEquals(this.bank.accounts.size(), 2);
    }

    @Test
    public void create_savings_many() {
        bank.createSavingsAccount(12345678, 0.1);
        bank.createSavingsAccount(23456789, 0.1);
        this.createProcessor.process("Create savings 34567890 0.6");
        assertTrue(this.bank.accountExists(34567890));
        assertEquals(this.bank.accounts.size(), 3);
    }

    @Test
    public void create_checking_many() {
        bank.createCheckingAccount(12345678, 0.1);
        bank.createCheckingAccount(23456789, 0.1);
        this.createProcessor.process("Create checking 34567890 0.01");
        assertTrue(this.bank.accountExists(34567890));
        assertEquals(this.bank.accounts.size(), 3);
    }

    @Test
    public void create_cd_many() {
        bank.createCDAccount(12345678, 2000, 0.1);
        bank.createCDAccount(23456789, 2000, 0.1);
        this.createProcessor.process("Create cd 34567890 1.2 2000");
        assertTrue(this.bank.accountExists(34567890));
        assertEquals(this.bank.accounts.size(), 3);
    }
}
