package banking;

public class CreateValidator extends BaseValidator {

	public CreateValidator(Bank bank) {
		super(bank);
	}

	@Override
	public boolean validate(String command) {
		String action, quickId;
		double apr;

		try {
			action = command.split(" ")[0].toLowerCase();
			quickId = command.split(" ")[2];
			apr = Double.parseDouble(command.split(" ")[3]);
		} catch (Exception e) {
			return false;
		}

		boolean isValidAction = action.equals("create");
		boolean isValidId = !bank.checkAccountExistsById(quickId) && isValidId(quickId);
		return isValidAction && isValidId && isValidApr(apr) && isValidCreateBalance(command)
				&& isValidNumberOfArgument(command, 5);
	}

	public boolean isValidCreateBalance(String command) {
		double balance = 0;
		boolean isBalanceSpecified;
		String accountType = "";
		try {
			accountType = command.split(" ")[1].toLowerCase();
			balance = Double.parseDouble(command.split(" ")[4]);
			isBalanceSpecified = true;
		} catch (Exception ignored) {
			isBalanceSpecified = false;
		}

		if (accountType.equals("checking") || accountType.equals("savings"))
			return !isBalanceSpecified;

		if (accountType.equals("cd") && isBalanceSpecified)
			return 1000 <= balance && balance <= 10000;

		return false;
	}
}
