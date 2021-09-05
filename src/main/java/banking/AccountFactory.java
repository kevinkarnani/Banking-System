package banking;

public class AccountFactory {
    public Account create(double amount, double apr) {
        return new CDAccount(amount, apr);
    }

    public Account create(double apr, String type) {
        return type.equals("checking") ? new CheckingAccount(apr) : new SavingsAccount(apr);
    }
}
