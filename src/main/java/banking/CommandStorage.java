package banking;

import java.util.ArrayList;

public class CommandStorage {
    public ArrayList<String> validCommands;
    public ArrayList<String> invalidCommands;

    public CommandStorage() {
        this.validCommands = new ArrayList<>();
        this.invalidCommands = new ArrayList<>();
    }

    public void addValidCommand(String command) {
        this.validCommands.add(command);
    }

    public void addInvalidCommand(String command) {
        this.invalidCommands.add(command);
    }

    public ArrayList<String> getValidCommands() {
        return this.validCommands;
    }

    public ArrayList<String> getInvalidCommands() {
        return this.invalidCommands;
    }
}
