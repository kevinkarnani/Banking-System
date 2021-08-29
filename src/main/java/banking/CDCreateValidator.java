package banking;

import java.util.ArrayList;

public class CDCreateValidator extends CreateValidator {
    public CDCreateValidator(Bank bank) {
        super(bank);
        this.expectedLength = 5;
        this.accountTypes = new ArrayList<>();
        this.accountTypes.add("cd");
    }

    @Override
    public boolean validate(String command) {
        command = command.toLowerCase();
        String[] words = command.split("\\s+");
        return this.validateWords(words) && this.validateAccountParams(words);
    }

    @Override
    public boolean validateWords(String[] words) {
        return super.validateWords(words) && words[4].matches("\\d*\\.?\\d+");
    }

    @Override
    public boolean validateAccountParams(String[] words) {
        return super.validateAccountParams(words) && this.bank.validateInitialCDAmount(Double.parseDouble(words[4]));
    }
}
