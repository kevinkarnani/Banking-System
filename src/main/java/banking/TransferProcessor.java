package banking;

public class TransferProcessor extends Processor {
    public TransferProcessor(Bank bank) {
        this.bank = bank;
    }

    @Override
    public void process(String command) {
        command = command.toLowerCase();
        String[] words = command.split("\\s+");
        this.bank.transfer(Integer.parseInt(words[1]), Integer.parseInt(words[2]), Double.parseDouble(words[3]));
    }
}
