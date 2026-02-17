public class CreateCommandValidator {
	Bank bank;

	public CreateCommandValidator(Bank bank) {
		this.bank = bank;
	}

	public boolean validate(String command) {
		String quickId = command.split(" ")[2];
		double apr;
		try {
			apr = Double.parseDouble(command.split(" ")[3]);
		} catch (Exception e) {
			return false;
		}

		boolean isIdInBank = bank.checkAccountExistsById(quickId);

		// should the responsibility in the validator or the account? when the account
		// has not even been created
		boolean isValidApr = 0 <= apr && apr <= 10;

		return !isIdInBank && isValidApr;
	}
}
