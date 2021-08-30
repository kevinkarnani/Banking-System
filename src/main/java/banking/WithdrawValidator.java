package banking;

public class WithdrawValidator extends Validator {
    public WithdrawValidator(Bank bank) {
        this.bank = bank;
    }

    @Override
    public boolean validate(String command) {
        command = command.toLowerCase();
        String[] words = command.split("\\s+");
        return this.validateWords(words) && this.validateAccountParams(words);
    }

    public boolean validateWords(String[] words) {
        return words.length == 3 && words[0].equals("withdraw") && words[1].matches("\\d+") &&
                words[2].matches("\\d*\\.?\\d+");
    }

    public boolean validateAccountParams(String[] words) {
        return this.bank.accountExists(Integer.parseInt(words[1])) &&
                this.bank.accountWithdrawUnderLimit(Double.parseDouble(words[2]), Integer.parseInt(words[1]));
    }
}
