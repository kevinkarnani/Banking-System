package banking;

import java.util.HashMap;

public class CommandProcessor {
    public Bank bank;
    public HashMap<String, Processor> processors;

    public CommandProcessor(Bank bank) {
        this.bank = bank;
        this.processors = new HashMap<>();
        this.processors.put("create", new CreateProcessor(this.bank));
        this.processors.put("deposit", new DepositProcessor(this.bank));
        this.processors.put("withdraw", new WithdrawProcessor(this.bank));
        this.processors.put("transfer", new TransferProcessor(this.bank));
        this.processors.put("pass", new PassTimeProcessor(this.bank));
    }

    public void processCommand(String command) {
        String[] words = command.toLowerCase().split("\\s+");
        this.processors.get(words[0]).process(command);
    }
}
