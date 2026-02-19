public class CreateCommandValidator extends CommandValidator {

	public CreateCommandValidator(Bank bank) {
		super(bank);
	}

	@Override
	public boolean validate(String command) {
		String accountType;
		try {
			accountType = command.split(" ")[1];
		} catch (Exception e) {
			return false;
		}

		String quickId;
		try {
			quickId = command.split(" ")[2];
		} catch (Exception e) {
			return false;
		}

		double apr = 0;
		try {
			apr = Double.parseDouble(command.split(" ")[3]);
		} catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
			return false;
		}

		double balance = 0;
		boolean isBalanceSpecified = false;
		try {
			balance = Double.parseDouble(command.split(" ")[4]);
			isBalanceSpecified = true;
		} catch (Exception e) {
			if (accountType.equals("cd")) {
				return false;
			}
		}

		boolean isValidApr = isValidApr(apr);

		boolean isIdNotInBank = !bank.checkAccountExistsById(quickId);
		boolean isValidId = isIdNotInBank && isValidId(quickId);

		boolean isValidBalance = false;

		switch (accountType.toLowerCase()) {
		case "checking":
			isValidBalance = !isBalanceSpecified;
			break;
		case "savings":
			isValidBalance = !isBalanceSpecified;
			break;
		case "cd":
			isValidBalance = 1000 <= balance && balance <= 10000;
			break;
		}

		return isValidId && isValidApr && isValidBalance;
	}

}
