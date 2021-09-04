package banking;

public abstract class Account {
    protected double amount;
    protected double apr;
    protected double maxDeposit;
    protected double maxWithdraw;
    protected int months = 0;

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

    public boolean validTransferAmount(double amount) {
        return this.validWithdrawAmount(amount);
    }

    public boolean validWithdrawAmount(double amount) {
        return amount <= this.maxWithdraw;
    }

    public void passTime() {
        if (this.amount < 100) {
            this.withdraw(25);
        }
        this.calculateAPR();
        this.months += 1;
    }

    public void calculateAPR() {
        this.amount += (this.apr / 100) / 12 * this.amount;
    }
}
