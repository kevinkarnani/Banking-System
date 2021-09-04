package banking;

import java.util.ArrayList;
import java.util.List;

public class CommandStorage {
    public ArrayList<String> invalidCommands;

    public CommandStorage() {
        this.invalidCommands = new ArrayList<>();
    }

    public List<String> getFormattedHistory(Bank bank) {
        List<String> formattedOutput = new ArrayList<>();
        bank.accountHistory.keySet().forEach(id -> formattedOutput.addAll(bank.getHistoryOfAccount(id)));
        return formattedOutput;
    }

    public void addInvalidCommand(String command) {
        this.invalidCommands.add(command);
    }

    public List<String> getInvalidCommands() {
        return this.invalidCommands;
    }

    public List<String> getFullOutput(Bank bank) {
        List<String> output = this.getFormattedHistory(bank);
        output.addAll(this.getInvalidCommands());
        return output;
    }
}
