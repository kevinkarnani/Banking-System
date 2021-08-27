public class CreateProcessor {
    public Bank bank;

    public CreateProcessor(Bank bank) {
        this.bank = bank;
    }

    public void process(String command) {
        String[] words = command.toLowerCase().split("\\s+");

        if (words[1].equals("checking")) {
            this.bank.createCheckingAccount(Integer.parseInt(words[2]), Double.parseDouble(words[3]));
        } else if (words[1].equals("savings")) {
            this.bank.createSavingsAccount(Integer.parseInt(words[2]), Double.parseDouble(words[3]));
        } else {
            this.bank.createCDAccount(Integer.parseInt(words[2]), Double.parseDouble(words[4]), Double.parseDouble(words[3]));
        }
    }
}
