public class DepositProcessor {
    public Bank bank;

    public DepositProcessor(Bank bank) {
        this.bank = bank;
    }

    public void process(String command) {
        String[] words = command.split("\\s+");
        this.bank.depositIntoAccount(Integer.parseInt(words[1]), Double.parseDouble(words[2]));
    }
}
