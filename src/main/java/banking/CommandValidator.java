package banking;

public class CommandValidator {
    public Bank bank;
    public CreateValidator createValidator;
    public DepositValidator depositValidator;

    public CommandValidator(Bank bank) {
        this.bank = bank;
        this.createValidator = new CreateValidator(this.bank);
        this.depositValidator = new DepositValidator(this.bank);
    }

    public boolean validateCommand(String command) {
        return this.createValidator.validate(command) || this.depositValidator.validate(command);
    }
}
