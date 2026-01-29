import java.util.HashMap;
import java.util.Map;

public class Bank {
	public Map<String, Account> accounts;

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

	public void deposit(String accountId, float v) {
		Account account = this.getAccounts().get(accountId);
		account.deposit(v);
	}

	public void withdraw(String accountId, float v) {
		Account account = this.getAccounts().get(accountId);
		account.withdraw(v);
	}
}
