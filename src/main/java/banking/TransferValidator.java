package banking;

public class TransferValidator extends BaseValidator {
	DepositValidator depositValidator;
	WithdrawValidator withdrawValidator;

	public TransferValidator(Bank bank) {
		super(bank);
		depositValidator = new DepositValidator(bank);
		withdrawValidator = new WithdrawValidator(bank);
	}

	public boolean validate(String command) {
		String srcId, desId, amount;
		try {
			srcId = command.split(" ")[1];
			desId = command.split(" ")[2];
			amount = command.split(" ")[3];
		} catch (Exception e) {
			return false;
		}
		boolean isValidSrcAndDes = validateSrcAndDes(srcId, desId, amount);

		return isValidNumberOfArgument(command, 4) && isValidSrcAndDes;
	}

	public boolean validateSrcAndDes(String srcId, String desId, String amount) {
		if (srcId.equals(desId)) {
			return false;
		}
		boolean isValidSrc = withdrawValidator.validate("withdraw" + " " + srcId + " " + amount);
		boolean isValidDes = depositValidator.validate("deposit" + " " + desId + " " + amount);
		return validateAccount(srcId) && validateAccount(desId) && isValidDes && isValidSrc;
	}

	public boolean validateAccount(String accountId) {
		if (!bank.checkAccountExistsById(accountId)) {
			return false;
		} else {
			return bank.getAccounts().get(accountId).isTransferable();
		}
	}
}
