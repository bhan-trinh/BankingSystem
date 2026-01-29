import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SavingsAccountTest {
	public static final float STARTING_BALANCE = 0;
	public static final float APR = 0.3F;

	@Test
	public void saving_account_starts_with_0_balance() {
		SavingsAccount savingsAccount = new SavingsAccount(APR);
		float actual = savingsAccount.getBalance();
		assertEquals(STARTING_BALANCE, actual);
	}
}
