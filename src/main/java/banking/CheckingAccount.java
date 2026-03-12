package banking;

public class CheckingAccount extends Account {

	private static final int CHECKING_STARTING_BALANCE = 0;
	private static final boolean IS_DEPOSITABLE = true;
	private static final double MAX_DEPOSIT_AMOUNT = 1000;
	private static final double MAX_WITHDRAW_AMOUNT = 400;

	public CheckingAccount(double apr) {
		super(apr, CHECKING_STARTING_BALANCE, IS_DEPOSITABLE, MAX_DEPOSIT_AMOUNT, MAX_WITHDRAW_AMOUNT);
	}

}
