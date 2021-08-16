package banking;

public class DepositValidator extends CommandValidator {
    public DepositValidator(Bank bank) {
        this.bank = bank;
    }

    @Override
    public boolean validate(String command) {
        command = command.toLowerCase();
        String[] words = command.split("\\s+");
        return words.length == 3 && words[0].equals("deposit") && words[1].matches("\\d+") && words[2]
                .matches("\\d*\\.?\\d+") && this.bank.accountExists(Integer.parseInt(words[1])) &&
                Float.parseFloat(words[2]) <= this.bank.accountDepositLimit(Integer.parseInt(words[1]));
    }
}
