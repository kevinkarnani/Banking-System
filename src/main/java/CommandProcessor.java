public class CommandProcessor {
    public Bank bank;
    public CreateProcessor createProcessor;
    public DepositProcessor depositProcessor;

    public CommandProcessor(Bank bank) {
        this.bank = bank;
        this.createProcessor = new CreateProcessor(this.bank);
        this.depositProcessor = new DepositProcessor(this.bank);
    }

    public void processCommand(String command) {
        String[] words = command.toLowerCase().split("\\s+");
        if (words[0].equals("create")) {
            this.createProcessor.process(command);
        } else {
            this.depositProcessor.process(command);
        }
    }
}
