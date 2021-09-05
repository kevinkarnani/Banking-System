package banking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CommandStorageTest {
    public CommandStorage commandStorage;
    public Bank bank;
    public ArrayList<String> output;

    @BeforeEach
    public void setUp() {
        this.commandStorage = new CommandStorage();
        this.bank = new Bank();
        this.output = new ArrayList<>();
    }

    @Test
    public void empty_invalid_command() {
        this.commandStorage.addInvalidCommand("deposit 1 100000");
        assertEquals(this.commandStorage.getInvalidCommands().size(), 1);
    }

    @Test
    public void one_invalid_command() {
        this.commandStorage.addInvalidCommand("deposit 1 100000");
        this.commandStorage.addInvalidCommand("deposit 2 100000");
        assertEquals(this.commandStorage.getInvalidCommands().size(), 2);
    }

    @Test
    public void many_invalid_command() {
        this.commandStorage.addInvalidCommand("deposit 1 100000");
        this.commandStorage.addInvalidCommand("deposit 2 100000");
        this.commandStorage.addInvalidCommand("deposit 3 100000");
        this.commandStorage.addInvalidCommand("deposit 4 100000");
        assertEquals(this.commandStorage.getInvalidCommands().size(), 4);
    }

    @Test
    public void create_command() {
        this.bank.createAccount(12345678, 0.1, "checking");
        this.output.add("Checking 12345678 0.00 0.10");
        assertEquals(this.commandStorage.getFormattedHistory(this.bank), this.output);
    }

    @Test
    public void deposit_command() {
        this.bank.createAccount(12345678, 0.1, "checking");
        this.bank.depositIntoAccount(12345678, 100);
        this.bank.addCommandToHistory(12345678, "Deposit 12345678 100");
        this.output.add("Checking 12345678 100.00 0.10");
        this.output.add("Deposit 12345678 100");
        assertEquals(this.commandStorage.getFormattedHistory(this.bank), this.output);
    }

    @Test
    public void withdraw_command() {
        this.bank.createAccount(12345678, 0.1, "checking");
        this.bank.depositIntoAccount(12345678, 100);
        this.bank.withdrawFromAccount(12345678, 50);
        this.bank.addCommandToHistory(12345678, "Deposit 12345678 100");
        this.bank.addCommandToHistory(12345678, "Withdraw 12345678 50");
        this.output.add("Checking 12345678 50.00 0.10");
        this.output.add("Deposit 12345678 100");
        this.output.add("Withdraw 12345678 50");
        assertEquals(this.commandStorage.getFormattedHistory(this.bank), this.output);
    }

    @Test
    public void transfer_command() {
        this.bank.createAccount(12345678, 0.1, "checking");
        this.bank.depositIntoAccount(12345678, 100);
        this.bank.createAccount(23456789, 0.1, "savings");
        this.bank.depositIntoAccount(23456789, 100);
        this.bank.transfer(12345678, 23456789, 100);
        this.bank.addCommandToHistory(12345678, "Deposit 12345678 100");
        this.bank.addCommandToHistory(23456789, "Deposit 23456789 100");
        this.bank.addCommandToHistory(12345678, "Transfer 12345678 23456789 100");
        this.bank.addCommandToHistory(23456789, "Transfer 12345678 23456789 100");
        this.output.add("Checking 12345678 0.00 0.10");
        this.output.add("Deposit 12345678 100");
        this.output.add("Transfer 12345678 23456789 100");
        this.output.add("Savings 23456789 200.00 0.10");
        this.output.add("Deposit 23456789 100");
        this.output.add("Transfer 12345678 23456789 100");
        assertEquals(this.commandStorage.getFormattedHistory(this.bank), this.output);
    }

    @Test
    public void pass_command() {
        this.bank.createAccount(12345678, 0.6, "savings");
        this.bank.depositIntoAccount(12345678, 700);
        this.bank.createAccount(98765432, 0.01, "checking");
        this.bank.depositIntoAccount(98765432, 300);
        this.bank.transfer(98765432, 12345678, 300);
        this.bank.addCommandToHistory(12345678, "Deposit 12345678 700");
        this.bank.addCommandToHistory(98765432, "Deposit 98765432 300");
        this.bank.addCommandToHistory(98765432, "Transfer 98765432 12345678 300");
        this.bank.addCommandToHistory(12345678, "Transfer 98765432 12345678 300");
        this.bank.passTime(1);
        this.bank.createAccount(23456789, 2000, 1.2);
        this.output.add("Savings 12345678 1000.50 0.60");
        this.output.add("Deposit 12345678 700");
        this.output.add("Transfer 98765432 12345678 300");
        this.output.add("Cd 23456789 2000.00 1.20");
        assertEquals(this.commandStorage.getFormattedHistory(this.bank), this.output);
    }
}
