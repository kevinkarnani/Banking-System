package banking;

public class TransferValidator extends Validator {
    public TransferValidator(Bank bank) {
        this.bank = bank;
    }

    @Override
    public boolean validate(String command) {
        command = command.toLowerCase();
        String[] words = command.split("\\s+");

        return words[0].equals("transfer") && words.length == 4 && this.validateIDs(words) && this.validTransfer(words);
    }

    public boolean validateIDs(String[] words) {
        return words[1].matches("\\d+") && words[2].matches("\\d+") &&
                this.bank.accountExists(Integer.parseInt(words[1])) &&
                this.bank.accountExists(Integer.parseInt(words[2]));
    }

    public boolean validTransfer(String[] words) {
        return words[3].matches("\\d*\\.?\\d+") && this.bank.validTransfer(Integer.parseInt(words[1]),
                Integer.parseInt(words[2]), Double.parseDouble(words[3]));
    }
}
