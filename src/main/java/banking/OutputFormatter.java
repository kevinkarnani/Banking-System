package banking;

import java.util.ArrayList;
import java.util.List;

public class OutputFormatter {
    Bank bank;

    public OutputFormatter(Bank bank) {
        this.bank = bank;
    }

    public ArrayList<String> format(List<String> commands) {
        ArrayList<String> output = new ArrayList<>();
        this.bank.accounts.forEach((id, account) -> {
            output.add(this.bank.getStateOfAccount(id));
            commands.forEach(command -> {
                if (command.contains(String.valueOf(id)) && !command.toLowerCase().contains("create")) {
                    output.add(command);
                }
            });
        });
        return output;
    }
}
