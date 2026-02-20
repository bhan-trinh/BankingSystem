public class DepositCommandValidator extends CommandValidator {
	public DepositCommandValidator(Bank bank) {
		super(bank);
	}

	@Override
	public boolean validate(String command) {
		String action;
		try {
			action = command.split(" ")[0];
		} catch (Exception e) {
			return false;
		}
		if (!action.equalsIgnoreCase("deposit"))
			return false;

		String quickId;
		try {
			quickId = command.split(" ")[1];
		} catch (Exception e) {
			return false;
		}

		Account depositAccount;
		if (!bank.checkAccountExistsById(quickId)) {
			return false;
		} else {
			depositAccount = bank.getAccounts().get(quickId);
		}

		if (!depositAccount.isDepositable())
			return false;

		double balance;
		try {
			balance = Double.parseDouble(command.split(" ")[2]);
		} catch (Exception e) {
			return false;
		}

		if (balance < 0 || balance > depositAccount.getMaxDepositAmount())
			return false;

		return true;
	}

}
