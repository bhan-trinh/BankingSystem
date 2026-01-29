public class SavingsAccount extends Account {

	public static final int SAVINGS_STARTING_BALANCE = 0;

	public SavingsAccount(float apr) {
		super(apr, SAVINGS_STARTING_BALANCE);
	}
}
