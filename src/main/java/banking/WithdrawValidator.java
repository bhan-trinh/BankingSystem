package banking;

public class WithdrawValidator extends BaseValidator {
	public WithdrawValidator(Bank bank) {
		super(bank);
	}

	public boolean validate(String command) {
		return true;
	}

}
