package banking;

import java.util.ArrayList;

public class CommandProcessor {
    public Bank bank;
    public ArrayList<Processor> processors;

    public CommandProcessor(Bank bank) {
        this.bank = bank;
        this.processors = new ArrayList<>();
        this.processors.add(new CreateProcessor(this.bank));
        this.processors.add(new DepositProcessor(this.bank));
        this.processors.add(new WithdrawProcessor(this.bank));
        this.processors.add(new TransferProcessor(this.bank));
        this.processors.add(new PassTimeProcessor(this.bank));
    }

    public void processCommand(String command) {
        ArrayList<String> types = new ArrayList<>();
        types.add("create");
        types.add("deposit");
        types.add("withdraw");
        types.add("transfer");
        types.add("pass");
        String[] words = command.toLowerCase().split("\\s+");
        this.processors.get(types.indexOf(words[0])).process(command);
    }
}
