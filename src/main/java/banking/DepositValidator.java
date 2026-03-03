package banking;

public class DepositValidator extends BaseValidator {
	public DepositValidator(Bank bank) {
		super(bank);
	}

	@Override
	public boolean validate(String command) {
		String action, quickId;
		double balance;
		try {
			action = command.split(" ")[0];
			quickId = command.split(" ")[1];
			balance = Double.parseDouble(command.split(" ")[2]);
		} catch (Exception e) {
			return false;
		}

		boolean isValidAction = action.equalsIgnoreCase("deposit");
		return isValidAction && isValidDepositBalance(quickId, balance);
	}

	public boolean isValidDepositBalance(String quickId, double balance) {
		Account depositAccount;
		if (!bank.checkAccountExistsById(quickId)) {
			return false;
		} else {
			depositAccount = bank.getAccounts().get(quickId);
		}

		boolean isValidBalance = !(balance < 0) && !(balance > depositAccount.getMaxDepositAmount());
		return isValidBalance && depositAccount.isDepositable();
	}
}
