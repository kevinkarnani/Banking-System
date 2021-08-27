package banking;

public class CDAccount extends Account {
    public CDAccount(double amount, double apr) {
        this.amount = amount;
        this.apr = apr;
        this.maxDeposit = -1;
    }
}
