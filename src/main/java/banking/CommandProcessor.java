package banking;

public class CommandProcessor {
    public Bank bank;
    public CreateProcessor createProcessor;
    public DepositProcessor depositProcessor;
    public PassTimeProcessor passTimeProcessor;

    public CommandProcessor(Bank bank) {
        this.bank = bank;
        this.createProcessor = new CreateProcessor(this.bank);
        this.depositProcessor = new DepositProcessor(this.bank);
        this.passTimeProcessor = new PassTimeProcessor(this.bank);
    }

    public void processCommand(String command) {
        String[] words = command.toLowerCase().split("\\s+");
        if (words[0].equals("create")) {
            this.createProcessor.process(command);
        } else if (words[0].equals("deposit")) {
            this.depositProcessor.process(command);
        } else {
            this.passTimeProcessor.process(command);
        }
    }
}
