import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BankTest {
    public Bank bank;

    @BeforeEach
    public void setUp() {
        this.bank = new Bank();
    }

    @Test
    public void empty_bank() {
        assertEquals(this.bank.accounts.size(), 0);
    }

    @Test
    public void create_checking_account() {
        this.bank.createCheckingAccount(12345678, .1);
        assertEquals(this.bank.accounts.size(), 1);
    }

    @Test
    public void create_savings_account() {
        this.bank.createSavingsAccount(12345678, .1);
        assertEquals(this.bank.accounts.size(), 1);
    }

    @Test
    public void create_cd_account() {
        this.bank.createCDAccount(12345678, 100, .1);
        assertEquals(this.bank.accounts.size(), 1);
    }

    @Test
    public void create_multiple() {
        this.bank.createCheckingAccount(1234567, .1);
        this.bank.createCDAccount(12345679, 100, .1);
        this.bank.createSavingsAccount(123456780, .1);
        assertEquals(this.bank.accounts.size(), 3);
    }

    @Test
    public void deposit_into_account() {
        this.bank.createSavingsAccount(12345678, .1);
        this.bank.depositIntoAccount(12345678, 100);
        assertEquals(this.bank.accounts.get(12345678).getAmount(), 100);
    }

    @Test
    public void withdraw_from_account() {
        this.bank.createCheckingAccount(12345678, .1);
        this.bank.depositIntoAccount(12345678, 100);
        this.bank.withdrawFromAccount(12345678, 70);
        assertEquals(this.bank.accounts.get(12345678).getAmount(), 30);
    }
}
