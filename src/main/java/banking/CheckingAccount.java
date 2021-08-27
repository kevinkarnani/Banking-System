package banking;

public class CheckingAccount extends Account {
    public CheckingAccount(double apr) {
        this.amount = 0;
        this.apr = apr;
        this.maxDeposit = 1000;
    }
}
