import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CreateValidatorTest {
    public CommandValidator commandValidator;
    public Bank bank;

    @BeforeEach
    public void setUp() {
        this.bank = new Bank();
        this.commandValidator = new CreateValidator(bank);
    }

    @Test
    public void create_savings_empty() {
        assertTrue(this.commandValidator.validate("Create savings 12345678 0.6"));
    }

    @Test
    public void create_savings_empty_case_insensitive() {
        assertTrue(this.commandValidator.validate("CrEaTe SaViNgS 12345678 0.6"));
    }

    @Test
    public void create_checking_empty() {
        assertTrue(this.commandValidator.validate("Create checking 12345678 0.01"));
    }

    @Test
    public void create_cd_empty() {
        assertTrue(this.commandValidator.validate("Create cd 12345678 1.2 2000"));
    }

    @Test
    public void create_savings_one() {
        bank.createSavingsAccount(12345678, 0.1);
        assertTrue(this.commandValidator.validate("Create savings 23456789 0.6"));
    }

    @Test
    public void create_checking_one() {
        bank.createCheckingAccount(12345678, 0.1);
        assertTrue(this.commandValidator.validate("Create checking 23456789 0.01"));
    }

    @Test
    public void create_cd_one() {
        bank.createCDAccount(12345678, 2000, 0.1);
        assertTrue(this.commandValidator.validate("Create cd 23456789 1.2 2000"));
    }

    @Test
    public void create_savings_many() {
        bank.createSavingsAccount(12345678, 0.1);
        bank.createSavingsAccount(23456789, 0.1);
        assertTrue(this.commandValidator.validate("Create savings 34567890 0.6"));
    }

    @Test
    public void create_checking_many() {
        bank.createCheckingAccount(12345678, 0.1);
        bank.createCheckingAccount(23456789, 0.1);
        assertTrue(this.commandValidator.validate("Create checking 34567890 0.01"));
    }

    @Test
    public void create_cd_many() {
        bank.createCDAccount(12345678, 2000, 0.1);
        bank.createCDAccount(23456789, 2000, 0.1);
        assertTrue(this.commandValidator.validate("Create cd 34567890 1.2 2000"));
    }

    @Test
    public void create_savings_over_max_apr() {
        assertFalse(this.commandValidator.validate("Create savings 12345678 11"));
    }

    @Test
    public void create_savings_under_min_apr() {
        assertFalse(this.commandValidator.validate("Create savings 12345678 -1"));
    }

    @Test
    public void create_savings_invalid_id() {
        assertFalse(this.commandValidator.validate("Create savings 1 0.6"));
    }

    @Test
    public void create_savings_invalid_apr() {
        assertFalse(this.commandValidator.validate("Create savings 12345678 1e-1"));
    }

    @Test
    public void create_savings_invalid_create() {
        assertFalse(this.commandValidator.validate("crate savings 12345678 0.1"));
    }

    @Test
    public void create_savings_invalid_account_type() {
        assertFalse(this.commandValidator.validate("Create nonexistent 12345678 0.1"));
    }

    @Test
    public void create_cd_invalid_amount() {
        assertFalse(this.commandValidator.validate("Create cd 12345678 1.2 10e5"));
    }

    @Test
    public void create_savings_missing_apr() {
        assertFalse(this.commandValidator.validate("Create savings 12345678"));
    }

    @Test
    public void create_savings_missing_id() {
        assertFalse(this.commandValidator.validate("Create savings 0.1"));
    }

    @Test
    public void create_savings_missing_account_type() {
        assertFalse(this.commandValidator.validate("Create 12345678 12345678"));
    }

    @Test
    public void create_savings_missing_create() {
        assertFalse(this.commandValidator.validate("savings 12345678 0.1"));
    }

    @Test
    public void create_cd_missing_amount() {
        assertFalse(this.commandValidator.validate("create cd 12345678 0.1"));
    }

    @Test
    public void create_cd_over_max_initial() {
        assertFalse(this.commandValidator.validate("Create cd 12345678 1.2 100000"));
    }

    @Test
    public void create_cd_under_min_initial() {
        assertFalse(this.commandValidator.validate("Create cd 12345678 1.2 100"));
    }
}
