public class BaseValidator {
	private static final int ID_LENGTH = 8;
	private static final int MAX_APR = 10;
	private static final int MIN_APR = 0;

	Bank bank;

	public BaseValidator(Bank bank) {
		this.bank = bank;
	}

	public boolean validate(String command) {
		return false;
	}

	public boolean isValidApr(double apr) {
		return MIN_APR <= apr && apr <= MAX_APR;
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
