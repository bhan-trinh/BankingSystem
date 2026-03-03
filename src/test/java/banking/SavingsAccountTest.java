package banking;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SavingsAccountTest {
	public static final float STARTING_BALANCE = 0;
	public static final double APR = 0.3;

	@Test
	public void saving_account_starts_with_0_balance() {
		SavingsAccount savingsAccount = new SavingsAccount(APR);
		double actual = savingsAccount.getBalance();
		assertEquals(STARTING_BALANCE, actual);
	}
}
