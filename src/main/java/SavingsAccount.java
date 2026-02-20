public class SavingsAccount extends Account {

	public static final int SAVINGS_STARTING_BALANCE = 0;
	private final boolean IS_DEPOSITABLE = true;
	private final double MAX_DEPOSIT_AMOUNT = 2500;

	public SavingsAccount(double apr) {
		super(apr, SAVINGS_STARTING_BALANCE);
	}

	@Override
	public boolean isDepositable() {
		return IS_DEPOSITABLE;
	}

	@Override
	public double getMaxDepositAmount() {
		return MAX_DEPOSIT_AMOUNT;
	}
}
