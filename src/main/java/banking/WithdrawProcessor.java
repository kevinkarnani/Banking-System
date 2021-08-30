package banking;

public class WithdrawProcessor extends Processor {
    public WithdrawProcessor(Bank bank) {
        this.bank = bank;
    }

    @Override
    public void process(String command) {
        String[] words = command.split("\\s+");
        this.bank.withdrawFromAccount(Integer.parseInt(words[1]), Double.parseDouble(words[2]));
    }
}
