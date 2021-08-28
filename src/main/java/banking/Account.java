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

    public boolean validDepositAmount(double amount) {
        return amount <= this.maxDeposit;
    }

    public void passTime() {
        if (this.amount < 100) {
            this.withdraw(25);
        }
        this.calculateAPR();
    }

    public void calculateAPR() {
        this.amount += (this.apr / 100) / 12 * this.amount;
    }
}
