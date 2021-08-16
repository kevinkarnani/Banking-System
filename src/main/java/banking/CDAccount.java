package banking;

public class CDAccount extends Account {
    public CDAccount(int amount, double apr) {
        this.amount = amount;
        this.apr = apr;
        this.maxDeposit = -1;
    }
}
