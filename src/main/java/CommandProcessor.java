public class CommandProcessor {
    public Bank bank;
    public CreateProcessor createProcessor;
    public DepositProcessor depositProcessor;

    public CommandProcessor(Bank bank) {
        this.bank = bank;
        this.createProcessor = new CreateProcessor(this.bank);
        this.depositProcessor = new DepositProcessor(this.bank);
    }
}
