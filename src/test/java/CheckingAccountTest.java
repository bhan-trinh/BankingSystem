import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CheckingAccountTest {
	public static final float STARTING_BALANCE = 0;
	public static final float APR = 0.3F;

	@Test
	public void checking_account_starts_with_0_balance() {
		CheckingAccount checkingAccount = new CheckingAccount(APR);

		float actual = checkingAccount.getBalance();
		assertEquals(STARTING_BALANCE, actual);
	}
}
