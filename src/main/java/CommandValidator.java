public class CommandValidator {
	CreateCommandValidator createCommandValidator;

	public CommandValidator(Bank bank) {
		createCommandValidator = new CreateCommandValidator(bank);
	}

	public boolean validate(String command) {
		boolean actual = false;

		String action = command.split(" ")[0];
		action = action.toLowerCase();

		switch (action) {
		case "create":
			actual = createCommandValidator.validate(command);
			break;
		case "deposit":
			break;

		}
		return actual;
	}
}
