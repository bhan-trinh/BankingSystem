package banking;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Bank {
	private static final int MIN_PASS_TIME_MONTH = 1;
	private static final int MAX_PASS_TIME_MONTH = 60;
	private static final int ACCOUNT_MONTHLY_FEE = 25;
	private final Map<String, Account> accounts;

	public Bank() {
		accounts = new HashMap<>();
	}

	public Map<String, Account> getAccounts() {
		return accounts;
	}

	public void addAccount(String id, Account account) {
		accounts.put(id, account);
		account.setId(id);
	}

	public void closeAccount(String accountId) {
		accounts.remove(accountId);
	}

	public void deposit(String accountId, double v) {
		Account account = this.getAccounts().get(accountId);
		account.deposit(v);
	}

	public double withdraw(String accountId, double amountWithdrawn) {
		Account account = this.getAccounts().get(accountId);
		return account.withdraw(amountWithdrawn);
	}

	public boolean checkAccountExistsById(String quickId) {
		return accounts.get(quickId) != null;
	}

	public double getMinPassTimeMonth() {
		return MIN_PASS_TIME_MONTH;
	}

	public double getMaxPassTimeMonth() {
		return MAX_PASS_TIME_MONTH;
	}

	public void pass(int month) {
		List<String> accountsToClose = new ArrayList<>();
		for (int i = 0; i < month; i++) {
			for (var account : this.getAccounts().entrySet()) {
				double balance = account.getValue().getBalance();
				if (balance == 0) {
					accountsToClose.add(account.getKey());
					continue;
				}
				passMonthForAccount(account.getKey());
			}
		}

		closeAccounts(accountsToClose);
	}

	private void closeAccounts(List<String> accountsToClose) {
		for (String id : accountsToClose) {
			this.getAccounts().remove(id);
		}
	}

	private void passMonthForAccount(String accountId) {
		double balance = this.getAccounts().get(accountId).getBalance();

		if (balance < 100) {
			applyMonthlyFees(accountId);
		}

		this.getAccounts().get(accountId).applyAPR();
		this.getAccounts().get(accountId).processMonth();

	}

	private void applyMonthlyFees(String accountId) {
		this.deduct(accountId, ACCOUNT_MONTHLY_FEE);
	}

	private void deduct(String accountId, double amount) {
		Account account = this.getAccounts().get(accountId);
		account.deduct(amount);
	}
}
