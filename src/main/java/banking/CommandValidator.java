package banking;

import java.util.ArrayList;

public class CommandValidator {
    public Bank bank;
    public ArrayList<Validator> validators;

    public CommandValidator(Bank bank) {
        this.bank = bank;
        this.validators = new ArrayList<>();
        this.validators.add(new CreateValidator(this.bank));
        this.validators.add(new DepositValidator(this.bank));
        this.validators.add(new WithdrawValidator(this.bank));
        this.validators.add(new TransferValidator(this.bank));
        this.validators.add(new PassTimeValidator());
    }

    public boolean validateCommand(String command) {
        return this.validators.stream().map(v -> v.validate(command)).reduce(Boolean::logicalOr).orElse(false);
    }
}
