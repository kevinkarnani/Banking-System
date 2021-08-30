package banking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TransferValidatorTest {
    public Bank bank;
    public TransferValidator commandValidator;

    @BeforeEach
    public void setUp() {
        this.bank = new Bank();
        this.bank.createSavingsAccount(12345678, 1);
        this.bank.createCheckingAccount(23456789, 1);
        this.bank.createCDAccount(34567890, 1000, 1);
        this.commandValidator = new TransferValidator(bank);
    }

    @Test
    public void transfer_between_savings_and_checking_accounts() {
        this.bank.depositIntoAccount(12345678, 100);
        assertTrue(this.commandValidator.validate("transfer 12345678 23456789 50"));
    }

    @Test
    public void transfer_between_checking_and_savings_accounts() {
        this.bank.depositIntoAccount(23456789, 100);
        assertTrue(this.commandValidator.validate("transfer 23456789 12345678 50"));
    }

    @Test
    public void transfer_over_balance_is_valid() {
        this.bank.depositIntoAccount(12345678, 100);
        assertTrue(this.commandValidator.validate("transfer 12345678 23456789 200"));
    }

    @Test
    public void transfer_over_checking_max_is_invalid() {
        this.bank.depositIntoAccount(23456789, 10000);
        assertFalse(this.commandValidator.validate("transfer 23456789 12345678 10000"));
    }

    @Test
    public void transfer_over_savings_max_is_invalid() {
        this.bank.depositIntoAccount(12345678, 10000);
        assertFalse(this.commandValidator.validate("transfer 12345678 23456789 10000"));
    }

    @Test
    public void cannot_transfer_to_cd() {
        this.bank.depositIntoAccount(12345678, 100);
        assertFalse(this.commandValidator.validate("transfer 12345678 34567890 100"));
    }

    @Test
    public void cannot_transfer_from_cd() {
        assertFalse(this.commandValidator.validate("transfer 34567890 12345678 100"));
    }

    @Test
    public void no_alphanumeric_id() {
        assertFalse(this.commandValidator.validate("transfer a2345678 b2345678 100"));
    }

    @Test
    public void no_alphanumeric_amount() {
        assertFalse(this.commandValidator.validate("transfer 12345678 23456789 a5"));
    }

    @Test
    public void missing_transfer() {
        assertFalse(this.commandValidator.validate("12345678 23456789 100"));
    }

    @Test
    public void missing_origin_id() {
        assertFalse(this.commandValidator.validate("transfer 23456789 100"));
    }

    @Test
    public void missing_recipient_id() {
        assertFalse(this.commandValidator.validate("transfer 12345678 100"));
    }

    @Test
    public void missing_both_ids() {
        assertFalse(this.commandValidator.validate("transfer 100"));
    }

    @Test
    public void missing_amount() {
        assertFalse(this.commandValidator.validate("transfer 12345678 23456789"));
    }
}
