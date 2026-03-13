package banking;

public class WithdrawValidator extends BaseValidator {
	public WithdrawValidator(Bank bank) {
		super(bank);
	}

	public boolean validate(String command) {
		String quickId;
		double amount;
		try {
			quickId = command.split(" ")[1];
			amount = Double.parseDouble(command.split(" ")[2]);
		} catch (Exception e) {
			return false;
		}

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
