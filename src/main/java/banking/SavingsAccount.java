package banking;

public class SavingsAccount extends Account {

	private static final int SAVINGS_STARTING_BALANCE = 0;
	private static final boolean IS_DEPOSITABLE = true;
	private static final double MAX_DEPOSIT_AMOUNT = 2500;
	private static final double MAX_WITHDRAW_AMOUNT = 1000;
	private boolean isWithdrawnThisMonth = false;

	public SavingsAccount(double apr) {
		super(apr, SAVINGS_STARTING_BALANCE, IS_DEPOSITABLE, MAX_DEPOSIT_AMOUNT, MAX_WITHDRAW_AMOUNT);
	}

	@Override
	public void withdraw(double amount) {
		super.withdraw(amount);
		isWithdrawnThisMonth = true;
	}

	@Override
	public void processMonth() {
		isWithdrawnThisMonth = false;
	}

	@Override
	public boolean isWithdrawable() {
		return !isWithdrawnThisMonth;
	}
}
