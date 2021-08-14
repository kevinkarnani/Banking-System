import java.util.ArrayList;

public class CommandStorage {
    public ArrayList<String> invalidCommands;

    public CommandStorage() {
        this.invalidCommands = new ArrayList<>();
    }

    public void addInvalidCommand(String command) {
        this.invalidCommands.add(command);
    }

    public ArrayList<String> getInvalidCommands() {
        return this.invalidCommands;
    }
}
