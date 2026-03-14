package banking;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CDAccountTest {

	public static final double APR = 0.3;
	public static final double STARTING_BALANCE = 100.50;

	@Test
	public void cd_account_starts_with_supplied_balance() {
		CDAccount cdAccount = new CDAccount(APR, STARTING_BALANCE);
		double actual = cdAccount.getBalance();
		assertEquals(STARTING_BALANCE, actual);
	}

}
