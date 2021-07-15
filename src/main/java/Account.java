public class Account {
    protected double amount;
    protected double apr;

    public Account(double amount, double apr) {
        this.amount = amount;
        this.apr = apr;
    }

    public Account(double apr) {
        this.amount = 0;
        this.apr = apr;
    }

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
