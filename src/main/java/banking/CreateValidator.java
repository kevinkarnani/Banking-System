package banking;

import java.util.ArrayList;

public class CreateValidator {
    public Bank bank;
    public ArrayList<String> accountTypes;

    public CreateValidator(Bank bank) {
        this.bank = bank;
        this.accountTypes = new ArrayList<>();

        this.accountTypes.add("savings");
        this.accountTypes.add("checking");
        this.accountTypes.add("cd");
    }

    public boolean validate(String command) {
        int len = 4;
        command = command.toLowerCase();
        String[] words = command.split("\\s+");

        if (command.contains("cd")) {
            len = 5;
        }

        return this.validateWords(words, len) && this.validateAccountParams(words, len);
    }

    public boolean validateWords(String[] words, int len) {
        boolean flag = words.length == len && words[0].equals("create") && this.accountTypes.contains(words[1]) &&
                words[2].matches("\\d+") && words[3].matches("\\d*\\.?\\d+") && words[2].length() == 8;
        if (len == 5) {
            flag = flag && words[4].matches("\\d*\\.?\\d+");
        }
        return flag;
    }

    public boolean validateAccountParams(String[] words, int len) {
        boolean flag = !this.bank.accountExists(Integer.parseInt(words[2])) && Double.parseDouble(words[3]) <= 10 &&
                Double.parseDouble(words[3]) >= 0;
        if (len == 5) {
            flag = flag && Double.parseDouble(words[4]) <= 10000 && Double.parseDouble(words[4]) >= 1000;
        }
        return flag;
    }
}
