import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AccountTest {
	public static final float DEPOSIT_AMOUNT = 100.50F;
	public static final float WITHDRAW_AMOUNT = 30.50F;
	public static final float APR = 0.3F;
	CheckingAccount checkingAccount;

	@BeforeEach
	public void setUp() {
		checkingAccount = new CheckingAccount(APR);
	}

	@Test
	public void account_APR_same_as_supplied_APR_value() {

		float actual = checkingAccount.getAPR();
		assertEquals(APR, actual);
	}

	@Test
	public void account_balance_increases_by_amount_deposited() {
		checkingAccount.deposit(DEPOSIT_AMOUNT);
		float actual = checkingAccount.getBalance();
		assertEquals(DEPOSIT_AMOUNT, actual);
	}

	@Test
	public void account_balance_decreases_by_amount_withdrawn() {
		checkingAccount.deposit(DEPOSIT_AMOUNT);
		checkingAccount.withdraw(WITHDRAW_AMOUNT);
		float actual = checkingAccount.getBalance();
		assertEquals(DEPOSIT_AMOUNT - WITHDRAW_AMOUNT, actual);
	}

	@Test
	public void account_balance_cannot_go_below_0_after_withdrawing() {
		checkingAccount.deposit(DEPOSIT_AMOUNT);
		checkingAccount.withdraw(DEPOSIT_AMOUNT + 1);
		float actual = checkingAccount.getBalance();
		assertEquals(0, actual);
	}

	@Test
	public void deposit_twice_works_as_expected() {
		checkingAccount.deposit(DEPOSIT_AMOUNT);
		checkingAccount.deposit(DEPOSIT_AMOUNT);
		float actual = checkingAccount.getBalance();
		assertEquals(DEPOSIT_AMOUNT * 2, actual);
	}

	@Test
	public void withdraw_twice_works_as_expected() {
		checkingAccount.deposit(DEPOSIT_AMOUNT);
		checkingAccount.withdraw(WITHDRAW_AMOUNT);
		checkingAccount.withdraw(WITHDRAW_AMOUNT);
		float actual = checkingAccount.getBalance();
		assertEquals(DEPOSIT_AMOUNT - WITHDRAW_AMOUNT * 2, actual);
	}
}
