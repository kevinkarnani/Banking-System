package banking;

public class PassTimeProcessor {
    public Bank bank;

    public PassTimeProcessor(Bank bank) {
        this.bank = bank;
    }

    public void process(String command) {
        String[] words = command.split("\\s+");
        this.bank.passTime(Integer.parseInt(words[1]));
    }
}
