package banking;

public class PassTimeValidator extends BaseValidator {
	public PassTimeValidator(Bank bank) {
		super(bank);
	}

	public boolean validate(String command) {
		double month;
		try {
			month = Double.parseDouble(command.split(" ")[1]);
		} catch (Exception e) {
			return false;
		}

		return isValidNumberOfArgument(command, 2) && month >= bank.getMinPassTimeMonth()
				&& month <= bank.getMaxPassTimeMonth();
	}
}
