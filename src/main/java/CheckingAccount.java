public class CheckingAccount extends Account {

	public static final int CHECKING_STARTING_BALANCE = 0;

	public CheckingAccount(double apr) {
		super(apr, CHECKING_STARTING_BALANCE);
	}
}
