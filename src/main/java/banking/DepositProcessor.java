package banking;

public class DepositProcessor extends Processor {
    public DepositProcessor(Bank bank) {
        this.bank = bank;
    }

    @Override
    public void process(String command) {
        String[] words = command.split("\\s+");
        this.bank.depositIntoAccount(Integer.parseInt(words[1]), Double.parseDouble(words[2]), command);
    }
}
