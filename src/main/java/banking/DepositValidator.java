package banking;

public class DepositValidator extends BaseValidator {
	public DepositValidator(Bank bank) {
		super(bank);
	}

	@Override
	protected boolean isValidBalance(String quickId, double amount) {
		return isValidDepositBalance(quickId, amount);
	}

	public boolean isValidDepositBalance(String quickId, double amount) {
		Account depositAccount;
		if (!bank.checkAccountExistsById(quickId)) {
			return false;
		} else {
			depositAccount = bank.getAccounts().get(quickId);
		}

		boolean isValidBalance = !(amount < 0) && !(amount > depositAccount.getMaxDepositAmount());
		return isValidBalance && depositAccount.isDepositable();
	}
}
