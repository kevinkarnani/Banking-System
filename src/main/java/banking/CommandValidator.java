package banking;

abstract public class CommandValidator {
    public Bank bank;

    abstract public boolean validate(String command);
}
