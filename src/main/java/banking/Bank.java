package banking;

import java.util.TreeMap;

public class Bank {
    public TreeMap<Integer, Account> accounts;
    public int minCDAmount = 1000;
    public int maxCDAmount = 10000;
    public int minAPR = 0;
    public int maxAPR = 10;

    public Bank() {
        this.accounts = new TreeMap<>();
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

    public boolean accountWithdrawUnderLimit(double amount, int uuid) {
        return this.accounts.get(uuid).validWithdrawAmount(amount);
    }

    public void passTime(int time) {
        // Math.abs() to make the mutation tests not time out... what a joke
        for (int i = 0; Math.abs(i) < time; i++) {
            this.accounts.entrySet().removeIf(entry -> entry.getValue().getAmount() == 0);
            this.accounts.forEach((id, account) -> account.passTime());
        }
    }

    public boolean validateInitialCDAmount(double amount) {
        return this.minCDAmount <= amount && amount <= this.maxCDAmount;
    }

    public boolean validateInitialAPR(double apr) {
        return this.minAPR <= apr && apr <= this.maxAPR;
    }

    public void transfer(int uuidOrigin, int uuidRecipient, double amount) {
        if (amount > this.accounts.get(uuidOrigin).getAmount()) {
            amount = this.accounts.get(uuidOrigin).getAmount();
        }
        this.accounts.get(uuidOrigin).withdraw(amount);
        this.accounts.get(uuidRecipient).deposit(amount);
    }

    public boolean validTransfer(int uuidOrigin, int uuidRecipient, double amount) {
        return this.accounts.get(uuidOrigin).validTransferAmount(amount) &&
                this.accounts.get(uuidRecipient).validDepositAmount(amount);
    }

    public String getStateOfAccount(int id) {
        return this.accounts.get(id).getClassType() + String.format(" %d %.2f %.2f", id, this.accounts.get(id).
                getAmount(), this.accounts.get(id).apr);
    }
}
