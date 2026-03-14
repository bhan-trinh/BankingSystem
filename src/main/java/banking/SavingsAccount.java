package banking;

public class SavingsAccount extends Account {

	private static final int SAVINGS_STARTING_BALANCE = 0;
	private static final boolean IS_DEPOSITABLE = true;
	private static final boolean IS_TRANSFERABLE = true;
	private static final double MAX_DEPOSIT_AMOUNT = 2500;
	private static final double MAX_WITHDRAW_AMOUNT = 1000;
	private static final int MIN_WITHDRAW_AMOUNT = 0;
	private boolean isWithdrawnThisMonth = false;

	public SavingsAccount(double apr) {
		super(apr, SAVINGS_STARTING_BALANCE);
	}

	@Override
	public double withdraw(double amount) {
		super.withdraw(amount);
		isWithdrawnThisMonth = true;
		return amount;
	}

	@Override
	public boolean isDepositable() {
		return IS_DEPOSITABLE;
	}

	@Override
	public void processMonth() {
		isWithdrawnThisMonth = false;
	}

	@Override
	public boolean isWithdrawable() {
		return !isWithdrawnThisMonth;
	}

	@Override
	public boolean isTransferable() {
		return IS_TRANSFERABLE;
	}

	@Override
	public double getMaxDepositAmount() {
		return MAX_DEPOSIT_AMOUNT;
	}

	@Override
	public double getMaxWithdrawAmount() {
		return MAX_WITHDRAW_AMOUNT;
	}

	@Override
	public double getMinWithdrawAmount() {
		return MIN_WITHDRAW_AMOUNT;
	}
}
