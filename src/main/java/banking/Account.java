package banking;

public abstract class Account {
    protected double amount;
    protected double apr;
    protected double maxDeposit;

    public void deposit(double amount) {
        this.amount += amount;
    }

    public void withdraw(double amount) {
        this.amount -= amount;

        if (this.amount < 0) {
            this.amount = 0;
        }
    }

    public double getAmount() {
        return this.amount;
    }
}
