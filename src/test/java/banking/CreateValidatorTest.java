package banking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CreateValidatorTest {
    public CreateValidator commandValidator;
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
    public void create_savings_empty_zero_not_appended() {
        assertTrue(this.commandValidator.validate("CrEaTe SaViNgS 12345678 .6"));
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
        this.bank.createSavingsAccount(12345678, 0.1);
        assertTrue(this.commandValidator.validate("Create savings 23456789 0.6"));
    }

    @Test
    public void create_checking_one() {
        this.bank.createCheckingAccount(12345678, 0.1);
        assertTrue(this.commandValidator.validate("Create checking 23456789 0.01"));
    }

    @Test
    public void create_cd_one() {
        this.bank.createCDAccount(12345678, 2000, 0.1);
        assertTrue(this.commandValidator.validate("Create cd 23456789 1.2 2000"));
    }

    @Test
    public void create_savings_many() {
        this.bank.createSavingsAccount(12345678, 0.1);
        this.bank.createSavingsAccount(23456789, 0.1);
        assertTrue(this.commandValidator.validate("Create savings 34567890 0.6"));
    }

    @Test
    public void create_checking_many() {
        this.bank.createCheckingAccount(12345678, 0.1);
        this.bank.createCheckingAccount(23456789, 0.1);
        assertTrue(this.commandValidator.validate("Create checking 34567890 0.01"));
    }

    @Test
    public void create_cd_many() {
        this.bank.createCDAccount(12345678, 2000, 0.1);
        this.bank.createCDAccount(23456789, 2000, 0.1);
        assertTrue(this.commandValidator.validate("Create cd 34567890 1.2 2000"));
    }

    @Test
    public void create_cd_with_1000_is_valid() {
        assertTrue(this.commandValidator.validate("create cd 12345678 1.2 1000"));
    }

    @Test
    public void create_cd_with_10000_is_valid() {
        assertTrue(this.commandValidator.validate("create cd 12345678 1.2 10000"));
    }

    @Test
    public void create_cd_with_10_APR_is_valid() {
        assertTrue(this.commandValidator.validate("create cd 12345678 10 5000"));
    }

    @Test
    public void create_cd_with_0_APR_is_valid() {
        assertTrue(this.commandValidator.validate("create cd 12345678 0 5000"));
    }

    @Test
    public void create_savings_already_exists() {
        this.bank.createSavingsAccount(12345678, 0.1);
        assertFalse(this.commandValidator.validate("Create savings 12345678 0.6"));
    }

    @Test
    public void create_checking_already_exists() {
        this.bank.createCheckingAccount(12345678, 0.1);
        assertFalse(this.commandValidator.validate("Create checking 12345678 0.01"));
    }

    @Test
    public void create_cd_already_exists() {
        this.bank.createCDAccount(12345678, 2000, 0.1);
        assertFalse(this.commandValidator.validate("Create cd 12345678 1.2 2000"));
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
    public void create_savings_alphanumeric_id() {
        assertFalse(this.commandValidator.validate("Create savings 1234567a 0.6"));
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
        assertFalse(this.commandValidator.validate("Create 12345678 0.1"));
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

    @Test
    public void create_cd_alphanumeric_amount() {
        assertFalse(this.commandValidator.validate("Create cd 12345678 1.2 1e3"));
    }
}
