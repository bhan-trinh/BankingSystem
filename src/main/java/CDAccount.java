public class CDAccount extends Account {
	private static final boolean IS_DEPOSITABLE = false;

	public CDAccount(double apr, double balance) {
		super(apr, balance, IS_DEPOSITABLE, 0);
	}

	@Override
	public boolean isDepositable() {
		return IS_DEPOSITABLE;
	}
}
