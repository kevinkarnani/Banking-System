package banking;

public class SavingsAccount extends Account {
    private boolean withdrawnThisMonth = false;

    public SavingsAccount(double apr) {
        this.amount = 0;
        this.apr = apr;
        this.maxDeposit = 2500;
        this.maxWithdraw = 1000;
    }

    @Override
    public void withdraw(double amount) {
        super.withdraw(amount);
        this.withdrawnThisMonth = true;
    }

    @Override
    public boolean validWithdrawAmount(double amount) {
        return super.validWithdrawAmount(amount) && !this.withdrawnThisMonth;
    }

    @Override
    public void passTime() {
        super.passTime();
        this.withdrawnThisMonth = false;
    }

    @Override
    public String getClassType() {
        return "Savings";
    }
}
