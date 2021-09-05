package banking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MasterControlTest {
    MasterControl masterControl;
    List<String> input;
    List<String> output;

    @BeforeEach
    public void setUp() {
        this.input = new ArrayList<>();
        this.output = new ArrayList<>();
        Bank bank = new Bank();
        this.masterControl = new MasterControl(bank, new CommandValidator(bank),
                new CommandProcessor(bank), new CommandStorage());
    }

    @Test
    public void typo_in_create_command_is_invalid() {
        this.input.add("creat checking 12345678 1.0");

        List<String> actual = this.masterControl.start(this.input);

        assertSingleCommand("creat checking 12345678 1.0", actual);
    }

    @Test
    public void typo_in_deposit_command_is_invalid() {
        this.input.add("depositt 12345678 100");

        List<String> actual = this.masterControl.start(this.input);

        assertSingleCommand("depositt 12345678 100", actual);
    }

    @Test
    public void typo_in_withdraw_command_is_invalid() {
        this.input.add("withdrew 12345678 100");

        List<String> actual = this.masterControl.start(this.input);

        assertSingleCommand("withdrew 12345678 100", actual);
    }

    @Test
    public void typo_in_transfer_command_is_invalid() {
        this.input.add("trans 12345678 23456789 100");

        List<String> actual = this.masterControl.start(this.input);

        assertSingleCommand("trans 12345678 23456789 100", actual);
    }

    @Test
    public void typo_in_pass_command_is_invalid() {
        this.input.add("pas 1");

        List<String> actual = this.masterControl.start(this.input);

        assertSingleCommand("pas 1", actual);
    }

    @Test
    public void two_typo_commands_both_invalid() {
        this.input.add("creat checking 12345678 1.0");
        this.input.add("depositt 12345678 100");

        List<String> actual = this.masterControl.start(this.input);

        assertEquals(actual.size(), 2);
        assertEquals(actual.get(0), "creat checking 12345678 1.0");
        assertEquals(actual.get(1), "depositt 12345678 100");
    }

    @Test
    public void invalid_to_create_accounts_with_same_ID() {
        this.input.add("create checking 12345678 1.0");
        this.input.add("create checking 12345678 1.0");

        List<String> actual = this.masterControl.start(this.input);
        this.output.add("Checking 12345678 0.00 1.00");
        this.output.add("create checking 12345678 1.0");

        assertEquals(actual, this.output);
    }

    @Test
    public void valid_commands_return_nothing() {
        this.input.add("create checking 12345678 1.0");
        this.input.add("create savings 23456789 1.0");
        this.input.add("deposit 12345678 100");
        this.input.add("transfer 12345678 23456789 100");
        this.input.add("withdraw 23456789 100");
        this.input.add("pass 1");

        List<String> actual = this.masterControl.start(this.input);
        assertEquals(actual.size(), 0);
    }

    @Test
    public void create_command() {
        this.input.add("create checking 12345678 .1");
        this.output.add("Checking 12345678 0.00 0.10");

        List<String> actual = this.masterControl.start(this.input);
        assertSingleCommand("Checking 12345678 0.00 0.10", actual);
    }

    @Test
    public void deposit_command() {
        this.input.add("create checking 12345678 .1");
        this.input.add("Deposit 12345678 100");
        this.output.add("Checking 12345678 100.00 0.10");
        this.output.add("Deposit 12345678 100");

        List<String> actual = this.masterControl.start(this.input);
        assertEquals(actual, this.output);
    }

    @Test
    public void withdraw_command() {
        this.input.add("CrEaTe ChEcKiNg 12345678 .1");
        this.input.add("Deposit 12345678 100");
        this.input.add("Withdraw 12345678 50");
        this.output.add("Checking 12345678 50.00 0.10");
        this.output.add("Deposit 12345678 100");
        this.output.add("Withdraw 12345678 50");

        List<String> actual = this.masterControl.start(this.input);
        assertEquals(actual, this.output);
    }

    @Test
    public void transfer_command() {
        this.input.add("create ChEcKiNg 12345678 .1");
        this.input.add("Deposit 12345678 100");
        this.input.add("create Savings 23456789 .1");
        this.input.add("Deposit 23456789 100");
        this.input.add("Transfer 12345678 23456789 100");
        this.output.add("Checking 12345678 0.00 0.10");
        this.output.add("Deposit 12345678 100");
        this.output.add("Transfer 12345678 23456789 100");
        this.output.add("Savings 23456789 200.00 0.10");
        this.output.add("Deposit 23456789 100");
        this.output.add("Transfer 12345678 23456789 100");

        List<String> actual = this.masterControl.start(this.input);
        assertEquals(actual, this.output);
    }

    @Test
    public void pass_command() {
        this.input.add("create SaVings 12345678 0.1");
        this.input.add("deposit 12345678 700");
        this.input.add("create checking 23456789 0.1");
        this.input.add("create CD 34567890 1 4000");
        this.input.add("pass 1");
        this.output.add("Savings 12345678 700.06 0.10");
        this.output.add("deposit 12345678 700");
        this.output.add("Cd 34567890 4013.35 1.00");

        List<String> actual = this.masterControl.start(this.input);
        assertEquals(actual, this.output);
    }

    @Test
    void sample_make_sure_this_passes_unchanged_or_you_will_fail() {
        this.input.add("Create savings 12345678 0.6");
        this.input.add("Deposit 12345678 700");
        this.input.add("Deposit 12345678 5000");
        this.input.add("creAte cHecKing 98765432 0.01");
        this.input.add("Deposit 98765432 300");
        this.input.add("Transfer 98765432 12345678 300");
        this.input.add("Pass 1");
        this.input.add("Create cd 23456789 1.2 2000");
        List<String> actual = this.masterControl.start(this.input);

        assertEquals(5, actual.size());
        assertEquals("Savings 12345678 1000.50 0.60", actual.get(0));
        assertEquals("Deposit 12345678 700", actual.get(1));
        assertEquals("Transfer 98765432 12345678 300", actual.get(2));
        assertEquals("Cd 23456789 2000.00 1.20", actual.get(3));
        assertEquals("Deposit 12345678 5000", actual.get(4));
    }

    @Test
    public void deleted_account_does_not_have_history() {
        this.input.add("Create checking 12345678 0.6");
        this.input.add("Deposit 12345678 100");
        this.input.add("Withdraw 12345678 100");
        this.input.add("Pass 1");
        this.input.add("Create savings 12345678 0.6");
        List<String> actual = this.masterControl.start(this.input);
        assertSingleCommand("Savings 12345678 0.00 0.60", actual);
    }

    private void assertSingleCommand(String command, List<String> actual) {
        assertEquals(actual.size(), 1);
        assertEquals(actual.get(0), command);
    }
}
