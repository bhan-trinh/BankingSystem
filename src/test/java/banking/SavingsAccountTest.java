package banking;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class SavingsAccountTest {
	public static final float STARTING_BALANCE = 0;
	public static final double APR = 0.3;
	private static final int DEFAULT_AMOUNT = 100;

	@Test
	public void savings_account_starts_with_0_balance() {
		SavingsAccount savingsAccount = new SavingsAccount(APR);
		double actual = savingsAccount.getBalance();
		assertEquals(STARTING_BALANCE, actual);
	}

	@Test
	public void savings_account_has_1_withdraw_per_month() {
		SavingsAccount savingsAccount = new SavingsAccount(APR);
		savingsAccount.deposit(DEFAULT_AMOUNT);
		savingsAccount.withdraw(DEFAULT_AMOUNT);
		boolean actual = savingsAccount.isWithdrawable();
		assertFalse(actual);
	}
}
