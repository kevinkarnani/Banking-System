package banking;

public class CommandValidator {
    public Bank bank;
    public CreateValidator createValidator;
    public DepositValidator depositValidator;
    public PassTimeValidator passTimeValidator;

    public CommandValidator(Bank bank) {
        this.bank = bank;
        this.createValidator = new CreateValidator(this.bank);
        this.depositValidator = new DepositValidator(this.bank);
        this.passTimeValidator = new PassTimeValidator();
    }

    public boolean validateCommand(String command) {
        return this.createValidator.validate(command) || this.depositValidator.validate(command) ||
                this.passTimeValidator.validate(command);
    }
}
