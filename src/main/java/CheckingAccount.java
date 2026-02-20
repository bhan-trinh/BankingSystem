public class CheckingAccount extends Account {

	public static final int CHECKING_STARTING_BALANCE = 0;
	private final boolean IS_DEPOSITABLE = true;

	public CheckingAccount(double apr) {
		super(apr, CHECKING_STARTING_BALANCE);
	}

	@Override
	public boolean isDepositable() {
		return IS_DEPOSITABLE;
	}
}
