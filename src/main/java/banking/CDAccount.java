package banking;

public class CDAccount extends Account {
	private static final boolean IS_DEPOSITABLE = false;
	private static final double MAX_DEPOSIT_LIMIT = 0;
	private static final double MAX_WITHDRAW_LIMIT = Double.POSITIVE_INFINITY;
	private double monthsSinceCreated = 0;

	public CDAccount(double apr, double balance) {
		super(apr, balance, IS_DEPOSITABLE, MAX_DEPOSIT_LIMIT, MAX_WITHDRAW_LIMIT);
	}

	@Override
	public double getMinWithdrawAmount() {
		return this.getBalance();
	}

	@Override
	public void applyAPR() {
		for (int i = 0; i < 4; i++) {
			super.applyAPR();
		}
	}

	@Override
	public void processMonth() {
		monthsSinceCreated++;
	}

	@Override
	public boolean isWithdrawable() {
		return monthsSinceCreated >= 12;
	}
}
