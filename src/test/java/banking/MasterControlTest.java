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
    void sample_make_sure_this_passes_unchanged_or_you_will_fail() {
        input.add("Create savings 12345678 0.6");
        input.add("Deposit 12345678 700");
        input.add("Deposit 12345678 5000");
        input.add("creAte cHecKing 98765432 0.01");
        input.add("Deposit 98765432 300");
        input.add("Transfer 98765432 12345678 300");
        input.add("Pass 1");
        input.add("Create cd 23456789 1.2 2000");
        List<String> actual = masterControl.start(input);

        assertEquals(5, actual.size());
        assertEquals("Savings 12345678 1000.50 0.60", actual.get(0));
        assertEquals("Deposit 12345678 700", actual.get(1));
        assertEquals("Transfer 98765432 12345678 300", actual.get(2));
        assertEquals("Cd 23456789 2000.00 1.20", actual.get(3));
        assertEquals("Deposit 12345678 5000", actual.get(4));
    }

    private void assertSingleCommand(String command, List<String> actual) {
        assertEquals(actual.size(), 1);
        assertEquals(actual.get(0), command);
    }
}
