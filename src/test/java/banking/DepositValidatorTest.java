package banking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class DepositValidatorTest {
    public CommandValidator commandValidator;
    public Bank bank;

    @BeforeEach
    public void setUp() {
        this.bank = new Bank();
        this.bank.createCheckingAccount(12345678, 0.1);
        this.bank.createSavingsAccount(23456789, 0.1);
        this.bank.createCDAccount(34567890, 2000, 1);
        this.commandValidator = new DepositValidator(bank);
    }

    @Test
    public void deposit_checking() {
        assertTrue(this.commandValidator.validate("deposit 12345678 100"));
    }

    @Test
    public void deposit_checking_case_insensitive() {
        assertTrue(this.commandValidator.validate("DePoSiT 12345678 100"));
    }

    @Test
    public void deposit_checking_with_decimals() {
        assertTrue(this.commandValidator.validate("deposit 12345678 100.1"));
    }

    @Test
    public void deposit_savings() {
        assertTrue(this.commandValidator.validate("deposit 23456789 100"));
    }

    @Test
    public void deposit_cd() {
        assertFalse(this.commandValidator.validate("deposit 34567890 100"));
    }

    @Test
    public void deposit_nonexistent_account() {
        assertFalse(this.commandValidator.validate("deposit 12345679 100"));
    }

    @Test
    public void deposit_invalid_id() {
        assertFalse(this.commandValidator.validate("deposit 1 100"));
    }

    @Test
    public void deposit_invalid_amount() {
        assertFalse(this.commandValidator.validate("deposit 12345678 10e5"));
    }

    @Test
    public void deposit_negative_amount() {
        assertFalse(this.commandValidator.validate("deposit 12345678 -1"));
    }

    @Test
    public void deposit_invalid_deposit() {
        assertFalse(this.commandValidator.validate("deposat 12345678 100"));
    }

    @Test
    public void deposit_missing_id() {
        assertFalse(this.commandValidator.validate("deposit 100"));
    }

    @Test
    public void deposit_missing_amount() {
        assertFalse(this.commandValidator.validate("deposit 12345678"));
    }

    @Test
    public void deposit_missing_deposit() {
        assertFalse(this.commandValidator.validate("12345678 1000"));
    }

    @Test
    public void deposit_checking_above_max() {
        assertFalse(this.commandValidator.validate("deposit 12345678 5000"));
    }

    @Test
    public void deposit_savings_above_max() {
        assertFalse(this.commandValidator.validate("deposit 23456789 5000"));
    }
}
