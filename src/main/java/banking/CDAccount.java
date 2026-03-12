package banking;

public class CDAccount extends Account {
	private static final boolean IS_DEPOSITABLE = false;
	private static final double MAX_DEPOSIT_LIMIT = 0;
	private static final double MAX_WITHDRAW_LIMIT = Double.POSITIVE_INFINITY;

	public CDAccount(double apr, double balance) {
		super(apr, balance, IS_DEPOSITABLE, MAX_DEPOSIT_LIMIT, MAX_WITHDRAW_LIMIT);
	}

	@Override
	public double getMinWithdrawAmount() {
		return this.getBalance();
	}
}
