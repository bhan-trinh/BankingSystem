public class CommandValidator {
	private static final int ID_LENGTH = 8;

	Bank bank;

	public CommandValidator(Bank bank) {
		this.bank = bank;
	}

	public boolean validate(String command) {
		return false;
	}

	public boolean isValidApr(double apr) {
		return 0 <= apr && apr <= 10;
	}

	public boolean isValidId(String quickId) {
		boolean isNumeric = isIdNumeric(quickId);
		boolean isValidLength = quickId.length() == ID_LENGTH;

		return isNumeric && isValidLength;

	}

	private boolean isIdNumeric(String quickId) {
		if (quickId == null) {
			return false;
		}
		try {
			Double.parseDouble(quickId);
		} catch (NumberFormatException nfe) {
			return false;
		}
		return true;
	}
}
