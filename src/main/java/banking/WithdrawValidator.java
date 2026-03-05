package banking;

public class WithdrawValidator extends BaseValidator {
	public WithdrawValidator(Bank bank) {
		super(bank);
	}

	public boolean validate(String command) {
//		String action, quickId;
		double balance;
		try {
//            action = command.split(" ")[0];
//            quickId = command.split(" ")[1];
			balance = Double.parseDouble(command.split(" ")[2]);
		} catch (Exception e) {
			return false;
		}

		return !(balance > 1000) && balance >= 0;
//        boolean isValidAction = action.equalsIgnoreCase("deposit");
//        return isValidAction && isValidDepositBalance(quickId, balance);
	}

}
