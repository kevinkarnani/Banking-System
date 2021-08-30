package banking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MasterControlTest {
    MasterControl masterControl;
    List<String> input;

    @BeforeEach
    public void setUp() {
        this.input = new ArrayList<>();
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

        assertSingleCommand("create checking 12345678 1.0", actual);
    }

    @Test
    public void valid_commands_return_empty() {
        this.input.add("create checking 12345678 1.0");
        this.input.add("create savings 23456789 1.0");
        this.input.add("deposit 12345678 100");
        this.input.add("transfer 12345678 23456789 100");
        this.input.add("pass 1");

        List<String> actual = this.masterControl.start(this.input);
        assertEquals(actual.size(), 0);
    }

    private void assertSingleCommand(String command, List<String> actual) {
        assertEquals(actual.size(), 1);
        assertEquals(actual.get(0), command);
    }
}
