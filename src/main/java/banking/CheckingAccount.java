package banking;

public class CheckingAccount extends Account {
    public CheckingAccount(double apr) {
        this.amount = 0;
        this.apr = apr;
        this.maxDeposit = 1000;
        this.maxWithdraw = 400;
    }

    @Override
    public String getClassType() {
        return "Checking";
    }
}
