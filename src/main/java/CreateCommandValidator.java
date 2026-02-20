public class CreateCommandValidator extends CommandValidator {

	public CreateCommandValidator(Bank bank) {
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
		if (!action.equalsIgnoreCase("create"))
			return false;

		String quickId;
		try {
			quickId = command.split(" ")[2];
		} catch (Exception e) {
			return false;
		}

		if (bank.checkAccountExistsById(quickId) || !isValidId(quickId))
			return false;

		double apr;
		try {
			apr = Double.parseDouble(command.split(" ")[3]);
		} catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
			return false;
		}

		if (!isValidApr(apr))
			return false;

		String accountType;
		try {
			accountType = command.split(" ")[1];
		} catch (Exception e) {
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

		boolean isValidBalance = switch (accountType.toLowerCase()) {
		case "checking" -> !isBalanceSpecified;
		case "savings" -> !isBalanceSpecified;
		case "cd" -> 1000 <= balance && balance <= 10000;
		default -> false;
		};

		if (!isValidBalance) {
			return false;
		}

		return true;
	}

}
