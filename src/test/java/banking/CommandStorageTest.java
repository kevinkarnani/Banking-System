package banking;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CommandStorageTest {
    public CommandStorage commandStorage;

    @BeforeEach
    public void setUp() {
        this.commandStorage = new CommandStorage();
    }

    @Test
    public void empty_invalid_command() {
        this.commandStorage.addInvalidCommand("deposit 1 100000");
        Assertions.assertEquals(this.commandStorage.getInvalidCommands().size(), 1);
    }

    @Test
    public void one_invalid_command() {
        this.commandStorage.addInvalidCommand("deposit 1 100000");
        this.commandStorage.addInvalidCommand("deposit 2 100000");
        Assertions.assertEquals(this.commandStorage.getInvalidCommands().size(), 2);
    }

    @Test
    public void many_invalid_command() {
        this.commandStorage.addInvalidCommand("deposit 1 100000");
        this.commandStorage.addInvalidCommand("deposit 2 100000");
        this.commandStorage.addInvalidCommand("deposit 3 100000");
        this.commandStorage.addInvalidCommand("deposit 4 100000");
        Assertions.assertEquals(this.commandStorage.getInvalidCommands().size(), 4);
    }
}
