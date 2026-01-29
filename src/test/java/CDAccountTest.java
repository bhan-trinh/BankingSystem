import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CDAccountTest {

	public static final float APR = 0.3F;
	public static final float STARTING_BALANCE = 100.50F;

	@Test
	public void cd_account_starts_with_supplied_balance() {
		CDAccount cdAccount = new CDAccount(APR, STARTING_BALANCE);
		float actual = cdAccount.getBalance();
		assertEquals(STARTING_BALANCE, actual);
	}

}
