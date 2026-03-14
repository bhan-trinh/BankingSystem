public class CommandValidator {
	Bank bank;
	BaseValidator validator;

	public CommandValidator(Bank bank) {
		this.bank = bank;
	}

	public boolean validate(String command) {
		String action = command.split(" ")[0].toLowerCase();

		switch (action) {
		case "create":
			validator = new CreateValidator(bank);
			break;
		case "deposit":
			validator = new DepositValidator(bank);
			break;
		default:
			return false;
		}

		return validator.validate(command);
	}
}
