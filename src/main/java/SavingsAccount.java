public class SavingsAccount extends Account {

	private static final int SAVINGS_STARTING_BALANCE = 0;
	private static final boolean IS_DEPOSITABLE = true;
	private static final double MAX_DEPOSIT_AMOUNT = 2500;

	public SavingsAccount(double apr) {
		super(apr, SAVINGS_STARTING_BALANCE, IS_DEPOSITABLE, MAX_DEPOSIT_AMOUNT);
	}
}
