package banking;

public class PassTimeProcessor extends Processor {
    public PassTimeProcessor(Bank bank) {
        this.bank = bank;
    }

    @Override
    public void process(String command) {
        String[] words = command.split("\\s+");
        this.bank.passTime(Integer.parseInt(words[1]));
    }
}
