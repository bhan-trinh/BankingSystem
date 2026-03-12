package banking;

import java.util.HashMap;
import java.util.Map;

public class Bank {
	private static final int MIN_PASS_TIME_MONTH = 1;
	private static final int MAX_PASS_TIME_MONTH = 60;
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

	public void deposit(String accountId, double v) {
		Account account = this.getAccounts().get(accountId);
		account.deposit(v);
	}

	public void withdraw(String accountId, double v) {
		Account account = this.getAccounts().get(accountId);
		account.withdraw(v);
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

	public void pass(double month) {

	}
}
