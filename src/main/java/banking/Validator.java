package banking;

public abstract class Validator {
    protected Bank bank;

    public abstract boolean validate(String command);
}
