package banking;

public class WithdrawValidator extends BaseValidator {
	public WithdrawValidator(Bank bank) {
		super(bank);
	}

	@Override
	protected boolean isValidBalance(String quickId, double amount) {
		return isValidWithdrawBalance(quickId, amount);
	}

	public boolean isValidWithdrawBalance(String quickId, double balance) {
		Account withdrawAccount;
		if (!bank.checkAccountExistsById(quickId)) {
			return false;
		} else {
			withdrawAccount = bank.getAccounts().get(quickId);
		}

		boolean isValidBalance = balance >= withdrawAccount.getMinWithdrawAmount()
				&& balance <= withdrawAccount.getMaxWithdrawAmount();
		return isValidBalance && withdrawAccount.isWithdrawable();
	}

}
