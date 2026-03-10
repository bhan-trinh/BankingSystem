package banking;

public class WithdrawValidator extends BaseValidator {
	public WithdrawValidator(Bank bank) {
		super(bank);
	}

	public boolean validate(String command) {
//		String action, quickId;
		double amount;
		try {
//            action = command.split(" ")[0];
//            quickId = command.split(" ")[1];
			amount = Double.parseDouble(command.split(" ")[2]);
		} catch (Exception e) {
			return false;
		}

		return !(amount > 1000) && amount >= 0;
//        boolean isValidAction = action.equalsIgnoreCase("deposit");
//        return isValidAction && isValidDepositBalance(quickId, balance);
	}

}
