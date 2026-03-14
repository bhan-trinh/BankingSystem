package banking;

public class CDAccount extends Account {
	private static final boolean IS_DEPOSITABLE = false;
	private static final boolean IS_TRANSFERABLE = false;
	private static final double MAX_DEPOSIT_AMOUNT = 0;
	private static final double MAX_WITHDRAW_AMOUNT = Double.POSITIVE_INFINITY;
	private double monthsSinceCreated = 0;

	public CDAccount(double apr, double balance) {
		super(apr, balance);
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
	public boolean isDepositable() {
		return IS_DEPOSITABLE;
	}

	@Override
	public boolean isWithdrawable() {
		return monthsSinceCreated >= 12;
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
}
