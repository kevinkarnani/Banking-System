package banking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class OutputFormatterTest {
    Bank bank;
    OutputFormatter outputFormatter;
    ArrayList<String> input;
    ArrayList<String> output;

    @BeforeEach
    public void setUp() {
        this.bank = new Bank();
        this.outputFormatter = new OutputFormatter(this.bank);
        this.input = new ArrayList<>();
        this.output = new ArrayList<>();
    }

    @Test
    public void create_command() {
        this.bank.createCheckingAccount(12345678, 0.1);
        this.input.add("Create checking 12345678 0.1");
        this.output.add("Checking 12345678 0.00 0.10");
        assertEquals(this.outputFormatter.format(this.input), this.output);
    }

    @Test
    public void deposit_command() {
        this.bank.createCheckingAccount(12345678, 0.1);
        this.bank.depositIntoAccount(12345678, 100);
        this.input.add("Create checking 12345678 0.1");
        this.input.add("Deposit 12345678 100");
        this.output.add("Checking 12345678 100.00 0.10");
        this.output.add("Deposit 12345678 100");
        assertEquals(this.outputFormatter.format(this.input), this.output);
    }

    @Test
    public void withdraw_command() {
        this.bank.createCheckingAccount(12345678, 0.1);
        this.bank.depositIntoAccount(12345678, 100);
        this.bank.withdrawFromAccount(12345678, 50);
        this.input.add("Create checking 12345678 0.1");
        this.input.add("Deposit 12345678 100");
        this.input.add("Withdraw 12345678 50");
        this.output.add("Checking 12345678 50.00 0.10");
        this.output.add("Deposit 12345678 100");
        this.output.add("Withdraw 12345678 50");
        assertEquals(this.outputFormatter.format(this.input), this.output);
    }

    @Test
    public void transfer_command() {
        this.bank.createCheckingAccount(12345678, 0.1);
        this.bank.depositIntoAccount(12345678, 100);
        this.bank.createSavingsAccount(23456789, 0.1);
        this.bank.depositIntoAccount(23456789, 100);
        this.bank.transfer(12345678, 23456789, 100);
        this.input.add("Create checking 12345678 0.1");
        this.input.add("Deposit 12345678 100");
        this.input.add("Create savings 23456789 0.1");
        this.input.add("Deposit 23456789 100");
        this.input.add("Transfer 12345678 23456789 100");
        this.output.add("Checking 12345678 0.00 0.10");
        this.output.add("Deposit 12345678 100");
        this.output.add("Transfer 12345678 23456789 100");
        this.output.add("Savings 23456789 200.00 0.10");
        this.output.add("Deposit 23456789 100");
        this.output.add("Transfer 12345678 23456789 100");
        assertEquals(this.outputFormatter.format(this.input), this.output);
    }

    @Test
    public void pass_command() {
        this.bank.createSavingsAccount(12345678, 0.6);
        this.bank.depositIntoAccount(12345678, 700);
        this.bank.createCheckingAccount(98765432, 0.01);
        this.bank.depositIntoAccount(98765432, 300);
        this.bank.transfer(98765432, 12345678, 300);
        this.bank.passTime(1);
        this.bank.createCDAccount(23456789, 2000, 1.2);
        this.input.add("Create savings 12345678 0.6");
        this.input.add("Deposit 12345678 700");
        this.input.add("creAte cHecKing 98765432 0.01");
        this.input.add("Deposit 98765432 300");
        this.input.add("Transfer 98765432 12345678 300");
        this.input.add("Pass 1");
        this.input.add("Create cd 23456789 1.2 2000");
        this.output.add("Savings 12345678 1000.50 0.60");
        this.output.add("Deposit 12345678 700");
        this.output.add("Transfer 98765432 12345678 300");
        this.output.add("Cd 23456789 2000.00 1.20");
        assertEquals(this.outputFormatter.format(this.input), this.output);
    }
}
