package banking;

public class CheckingAccount extends Account {

	private static final int CHECKING_STARTING_BALANCE = 0;
	private static final boolean IS_DEPOSITABLE = true;
	private static final boolean IS_WITHDRAWABLE = true;
	private static final boolean IS_TRANSFERABLE = true;
	private static final double MAX_DEPOSIT_AMOUNT = 1000;
	private static final double MAX_WITHDRAW_AMOUNT = 400;
	private static final int MIN_WITHDRAW_AMOUNT = 0;

	public CheckingAccount(double apr) {
		super(apr, CHECKING_STARTING_BALANCE);
	}

	@Override
	public boolean isDepositable() {
		return IS_DEPOSITABLE;
	}

	@Override
	public boolean isWithdrawable() {
		return IS_WITHDRAWABLE;
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

	@Override
	public void processMonth() {
		return;
	}
}
