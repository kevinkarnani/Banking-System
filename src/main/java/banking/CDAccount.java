package banking;

public class CDAccount extends Account {
    private boolean withdrawn = false;

    public CDAccount(double amount, double apr) {
        this.amount = amount;
        this.apr = apr;
    }

    @Override
    public void calculateAPR() {
        // Math.abs() so the mutation tests dont time out...
        for (int i = 0; Math.abs(i) < 4; i++) {
            this.amount += (this.apr / 100) / 12 * this.amount;
        }
    }

    @Override
    public boolean validDepositAmount(double amount) {
        return false;
    }

    @Override
    public boolean validWithdrawAmount(double amount) {
        return this.months >= 12 && amount >= this.amount && !this.withdrawn;
    }

    @Override
    public void withdraw(double amount) {
        super.withdraw(amount);
        this.withdrawn = true;
    }

    @Override
    public boolean validTransferAmount(double amount) {
        return false;
    }

    @Override
    public String getClassType() {
        return "Cd";
    }
}
