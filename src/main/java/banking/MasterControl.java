package banking;

import java.util.List;

public class MasterControl {
    Bank bank;
    CommandValidator commandValidator;
    CommandProcessor commandProcessor;
    CommandStorage commandStorage;
    OutputFormatter outputFormatter;

    public MasterControl(Bank bank, CommandValidator commandValidator, CommandProcessor commandProcessor,
                         CommandStorage commandStorage) {
        this.bank = bank;
        this.commandValidator = commandValidator;
        this.commandProcessor = commandProcessor;
        this.commandStorage = commandStorage;
        this.outputFormatter = new OutputFormatter(this.bank);
    }

    public List<String> start(List<String> input) {
        for (String command : input) {
            if (this.commandValidator.validateCommand(command)) {
                this.commandProcessor.processCommand(command);
                this.commandStorage.addValidCommand(command);
            } else {
                this.commandStorage.addInvalidCommand(command);
            }
        }
        List<String> output = this.outputFormatter.format(this.commandStorage.getValidCommands());
        output.addAll(this.commandStorage.getInvalidCommands());
        return output;
    }
}
