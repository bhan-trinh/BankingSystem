public class CreateCommandValidator extends CommandValidator {

	public CreateCommandValidator(Bank bank) {
		super(bank);
	}

	@Override
	public boolean validate(String command) {
		String action, quickId;
		double apr;
		String accountType;

		try {
			action = command.split(" ")[0].toLowerCase();
			accountType = command.split(" ")[1].toLowerCase();
			quickId = command.split(" ")[2];
			apr = Double.parseDouble(command.split(" ")[3]);
		} catch (Exception e) {
			return false;
		}

		boolean isValidAction = action.equals("create");
		boolean isValidId = !bank.checkAccountExistsById(quickId) && isValidId(quickId);
		return isValidAction && isValidId && isValidApr(apr) && isValidCreateBalance(command, accountType);
	}

	public boolean isValidCreateBalance(String command, String accountType) {
		double balance = 0;
		boolean isBalanceSpecified = false;
		try {
			balance = Double.parseDouble(command.split(" ")[4]);
			isBalanceSpecified = true;
		} catch (Exception ignored) {
		}

		if (accountType.equals("checking") || accountType.equals("savings"))
			return !isBalanceSpecified;
		else if (accountType.equals("cd") && isBalanceSpecified)
			return 1000 <= balance && balance <= 10000;
		return false;
	}
}
