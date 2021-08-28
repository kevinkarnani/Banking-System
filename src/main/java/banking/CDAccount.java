package banking;

public class CDAccount extends Account {
    public CDAccount(double amount, double apr) {
        this.amount = amount;
        this.apr = apr;
        this.maxDeposit = -1;
    }

    @Override
    public void calculateAPR() {
        for (int i = 0; i < 4; i++) {
            this.amount += (this.apr / 100) / 12 * this.amount;
        }
    }
}
