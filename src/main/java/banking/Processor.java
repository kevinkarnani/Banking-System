package banking;

public abstract class Processor {
    protected Bank bank;

    public abstract void process(String command);
}
