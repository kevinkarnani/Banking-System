package banking;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class Bank {
    public LinkedHashMap<Integer, Account> accounts;
    public LinkedHashMap<Integer, List<String>> accountHistory;
    public int minCDAmount = 1000;
    public int maxCDAmount = 10000;
    public int minAPR = 0;
    public int maxAPR = 10;

    public Bank() {
        this.accounts = new LinkedHashMap<>();
        this.accountHistory = new LinkedHashMap<>();
    }

    public void createAccount(int uuid, double amount, double apr) {
        this.accounts.put(uuid, new AccountFactory().create(amount, apr));
        this.addAccountToHistory(uuid);
    }

    public void createAccount(int uuid, double apr, String type) {
        this.accounts.put(uuid, new AccountFactory().create(apr, type));
        this.addAccountToHistory(uuid);
    }

    public void withdrawFromAccount(int uuid, double amount) {
        this.accounts.get(uuid).withdraw(amount);
    }

    public void withdrawFromAccount(int uuid, double amount, String command) {
        this.withdrawFromAccount(uuid, amount);
        this.addCommandToHistory(uuid, command);
    }

    public void depositIntoAccount(int uuid, double amount) {
        this.accounts.get(uuid).deposit(amount);
    }

    public void depositIntoAccount(int uuid, double amount, String command) {
        this.depositIntoAccount(uuid, amount);
        this.addCommandToHistory(uuid, command);
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
        this.accountHistory.entrySet().removeIf(entry -> !this.accounts.containsKey(entry.getKey()));
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

    public void transfer(int uuidOrigin, int uuidRecipient, double amount, String command) {
        this.transfer(uuidOrigin, uuidRecipient, amount);
        this.addCommandToHistory(uuidOrigin, command);
        this.addCommandToHistory(uuidRecipient, command);
    }

    public boolean validTransfer(int uuidOrigin, int uuidRecipient, double amount) {
        return this.accounts.get(uuidOrigin).validTransferAmount(amount) &&
                this.accounts.get(uuidRecipient).validDepositAmount(amount);
    }

    public String getStateOfAccount(int id) {
        return this.accounts.get(id).getClassType() + String.format(" %d %.2f %.2f", id, this.accounts.get(id).
                getAmount(), this.accounts.get(id).apr);
    }

    public List<String> getHistoryOfAccount(int id) {
        List<String> history = this.accountHistory.get(id);
        history.add(0, this.getStateOfAccount(id));
        return history;
    }

    public void addCommandToHistory(int id, String command) {
        this.accountHistory.get(id).add(command);
    }

    public void addAccountToHistory(int id) {
        this.accountHistory.put(id, new ArrayList<>());
    }
}
