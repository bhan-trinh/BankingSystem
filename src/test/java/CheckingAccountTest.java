import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CheckingAccountTest {
	public static final float STARTING_BALANCE = 0;
	public static final double APR = 0.3;

	@Test
	public void checking_account_starts_with_0_balance() {
		CheckingAccount checkingAccount = new CheckingAccount(APR);

		double actual = checkingAccount.getBalance();
		assertEquals(STARTING_BALANCE, actual);
	}
}
