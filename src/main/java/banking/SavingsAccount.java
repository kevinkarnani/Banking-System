package banking;

public class SavingsAccount extends Account {
    public SavingsAccount(double apr) {
        this.amount = 0;
        this.apr = apr;
        this.maxDeposit = 2500;
    }
}