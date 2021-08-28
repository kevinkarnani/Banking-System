package banking;

import java.util.HashMap;

public class Bank {
    public HashMap<Integer, Account> accounts;

    public Bank() {
        this.accounts = new HashMap<>();
    }

    public void createCheckingAccount(int uuid, double apr) {
        this.accounts.put(uuid, new CheckingAccount(apr));
    }

    public void createSavingsAccount(int uuid, double apr) {
        this.accounts.put(uuid, new SavingsAccount(apr));
    }

    public void createCDAccount(int uuid, double amount, double apr) {
        this.accounts.put(uuid, new CDAccount(amount, apr));
    }

    public void withdrawFromAccount(int uuid, double amount) {
        this.accounts.get(uuid).withdraw(amount);
    }

    public void depositIntoAccount(int uuid, double amount) {
        this.accounts.get(uuid).deposit(amount);
    }

    public boolean accountExists(int uuid) {
        return this.accounts.containsKey(uuid);
    }

    public boolean accountDepositUnderLimit(double amount, int uuid) {
        return this.accounts.get(uuid).validDepositAmount(amount);
    }

    public void passTime(int time) {
        for (int i = 0; i < time; i++) {
            this.accounts.entrySet().removeIf(entry -> entry.getValue().amount == 0);
            this.accounts.forEach((id, account) -> account.passTime());
        }
    }
}
