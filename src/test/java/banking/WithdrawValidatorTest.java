package banking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class WithdrawValidatorTest {
    public WithdrawValidator commandValidator;
    public Bank bank;

    @BeforeEach
    public void setUp() {
        this.bank = new Bank();
        this.bank.createCheckingAccount(12345678, 0.1);
        this.bank.createSavingsAccount(23456789, 0.1);
        this.bank.createCDAccount(34567890, 2000, 1);
        this.bank.depositIntoAccount(12345678, 300);
        this.bank.depositIntoAccount(23456789, 1000);
        this.commandValidator = new WithdrawValidator(this.bank);
    }

    @Test
    public void withdraw_checking() {
        assertTrue(this.commandValidator.validate("withdraw 12345678 100"));
    }

    @Test
    public void withdraw_checking_case_insensitive() {
        assertTrue(this.commandValidator.validate("WiThDrAw 12345678 100"));
    }

    @Test
    public void withdraw_checking_with_decimals() {
        assertTrue(this.commandValidator.validate("withdraw 12345678 100.1"));
    }

    @Test
    public void withdraw_checking_over_balance() {
        assertTrue(this.commandValidator.validate("withdraw 12345678 400"));
    }

    @Test
    public void withdraw_savings() {
        assertTrue(this.commandValidator.validate("withdraw 23456789 100"));
    }

    @Test
    public void withdraw_full_cd_after_12_months() {
        this.bank.accounts.get(34567890).months = 12;
        assertTrue(this.commandValidator.validate("withdraw 34567890 2000"));
    }

    @Test
    public void withdraw_cd_too_early() {
        assertFalse(this.commandValidator.validate("withdraw 34567890 100"));
    }

    @Test
    public void withdraw_half_cd_after_12_months() {
        this.bank.accounts.get(34567890).months = 12;
        assertFalse(this.commandValidator.validate("withdraw 34567890 1000"));
    }

    @Test
    public void withdraw_nonexistent_account() {
        assertFalse(this.commandValidator.validate("withdraw 12345679 100"));
    }

    @Test
    public void withdraw_invalid_id() {
        assertFalse(this.commandValidator.validate("withdraw 1 100"));
    }

    @Test
    public void withdraw_invalid_amount() {
        assertFalse(this.commandValidator.validate("withdraw 12345678 10e5"));
    }

    @Test
    public void withdraw_alphanumeric_amount() {
        assertFalse(this.commandValidator.validate("withdraw 12345678 a5"));
    }

    @Test
    public void withdraw_negative_amount() {
        assertFalse(this.commandValidator.validate("withdraw 12345678 -1"));
    }

    @Test
    public void withdraw_invalid_withdraw() {
        assertFalse(this.commandValidator.validate("withdrew 12345678 100"));
    }

    @Test
    public void withdraw_missing_id() {
        assertFalse(this.commandValidator.validate("withdraw 100"));
    }

    @Test
    public void withdraw_missing_amount() {
        assertFalse(this.commandValidator.validate("withdraw 12345678"));
    }

    @Test
    public void withdraw_missing_withdraw() {
        assertFalse(this.commandValidator.validate("12345678 1000"));
    }

    @Test
    public void withdraw_checking_above_max() {
        assertFalse(this.commandValidator.validate("withdraw 12345678 500"));
    }

    @Test
    public void withdraw_savings_above_max() {
        assertFalse(this.commandValidator.validate("withdraw 23456789 1100"));
    }
}
