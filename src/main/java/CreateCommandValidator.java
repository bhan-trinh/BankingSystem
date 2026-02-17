public class CreateCommandValidator {
	Bank bank;

	public CreateCommandValidator(Bank bank) {
		this.bank = bank;
	}

	public boolean validate(String command) {
		String quickId = command.split(" ")[2];

		boolean isIdInBank = bank.checkAccountExistsById(quickId);
		return !isIdInBank;
	}
}
