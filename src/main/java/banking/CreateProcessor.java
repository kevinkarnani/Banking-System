package banking;

public class CreateProcessor extends Processor {
    public CreateProcessor(Bank bank) {
        this.bank = bank;
    }

    @Override
    public void process(String command) {
        String[] words = command.toLowerCase().split("\\s+");

        if (words.length == 4) {
            this.bank.createAccount(Integer.parseInt(words[2]), Double.parseDouble(words[3]), words[1]);
        } else {
            this.bank.createAccount(Integer.parseInt(words[2]), Double.parseDouble(words[4]), Double.parseDouble(words[3]));
        }
    }
}
