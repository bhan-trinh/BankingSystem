public class CreateCommandValidator extends CommandValidator {

	public CreateCommandValidator(Bank bank) {
		super(bank);
	}

	@Override
	public boolean validate(String command) {
//		String accountType = command.split(" ")[1];
		String quickId = command.split(" ")[2];
		double apr;
		try {
			apr = Double.parseDouble(command.split(" ")[3]);
		} catch (Exception e) {
			return false;
		}
//		double balance = Double.parseDouble(command.split(" ")[4]);

		boolean isValidApr = 0 <= apr && apr <= 10;

		boolean isIdCorrectLength = quickId.length() == 8;
		boolean isIdNumeric = isIdNumeric(quickId);
		boolean isIdNotInBank = !bank.checkAccountExistsById(quickId);
		boolean isValidId = isIdNumeric && isIdNotInBank && isIdCorrectLength;

		return isValidId && isValidApr;
	}

	public boolean isIdNumeric(String quickId) {
		if (quickId == null) {
			return false;
		}
		try {
			double d = Double.parseDouble(quickId);
		} catch (NumberFormatException nfe) {
			return false;
		}
		return true;
	}
}
