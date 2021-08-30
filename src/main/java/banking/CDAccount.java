package banking;

public class CDAccount extends Account {
    public CDAccount(double amount, double apr) {
        this.amount = amount;
        this.apr = apr;
        this.maxDeposit = -1;
        this.maxWithdraw = -1;
    }

    @Override
    public void calculateAPR() {
        // Math.abs() so the mutation tests dont time out...
        for (int i = 0; Math.abs(i) < 4; i++) {
            this.amount += (this.apr / 100) / 12 * this.amount;
        }
    }

    @Override
    public boolean validWithdrawAmount(double amount) {
        return super.validWithdrawAmount(amount) || (this.months >= 12 && amount >= this.amount);
    }

    @Override
    public boolean validTransferAmount(double amount) {
        return false;
    }
}
