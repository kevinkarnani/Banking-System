package banking;

import java.util.ArrayList;

public class CreateValidator extends Validator {
    public ArrayList<String> accountTypes;
    public int expectedLength;

    public CreateValidator(Bank bank) {
        this.bank = bank;
        this.accountTypes = new ArrayList<>();

        this.accountTypes.add("savings");
        this.accountTypes.add("checking");

        this.expectedLength = 4;
    }

    @Override
    public boolean validate(String command) {
        command = command.toLowerCase();
        String[] words = command.split("\\s+");

        if (command.contains("cd")) {
            return new CDCreateValidator(this.bank).validate(command);
        }

        return this.validateWords(words) && this.validateAccountParams(words);
    }

    public boolean validateWords(String[] words) {
        return words.length == this.expectedLength && words[0].equals("create") && this.accountTypes.contains(words[1])
                && words[2].matches("\\d+") && words[3].matches("\\d*\\.?\\d+") && words[2].length() == 8;
    }

    public boolean validateAccountParams(String[] words) {
        return !this.bank.accountExists(Integer.parseInt(words[2])) &&
                this.bank.validateInitialAPR(Double.parseDouble(words[3]));
    }
}
