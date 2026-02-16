import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class BankTest {
	public static final String CHECKING_ACCOUNT_ID = "12345678";
	public static final String CD_ACCOUNT_ID = "12345679";
	public static final double APR = 0.3F;
	public static final double CD_STARTING_BALANCE = 50F;
	public static final double DEPOSIT_AMOUNT = 100F;
	public static final double WITHDRAW_AMOUNT = 30F;
	Bank bank;
	CheckingAccount checkingAccount;
	CDAccount cdAccount;

	@BeforeEach
	public void setUp() {
		bank = new Bank();
		checkingAccount = new CheckingAccount(APR);
		cdAccount = new CDAccount(APR, CD_STARTING_BALANCE);
	}

	@Test
	public void bank_has_no_account_when_created() {
		boolean actual = bank.getAccounts().isEmpty();
		assertTrue(actual);
	}

	@Test
	public void bank_has_one_account_when_one_is_added() {

		bank.addAccount(CHECKING_ACCOUNT_ID, checkingAccount);
		int actual = bank.getAccounts().size();
		assertEquals(1, actual);
	}

	@Test
	public void bank_has_two_accounts_when_two_is_added() {
		bank.addAccount(CHECKING_ACCOUNT_ID, checkingAccount);
		bank.addAccount(CD_ACCOUNT_ID, cdAccount);

		int actual = bank.getAccounts().size();
		assertEquals(2, actual);
	}

	@Test
	public void bank_retrieves_right_account_when_retrieving() {
		bank.addAccount(CHECKING_ACCOUNT_ID, checkingAccount);
		bank.addAccount(CD_ACCOUNT_ID, cdAccount);

		String actual = bank.getAccounts().get(CHECKING_ACCOUNT_ID).getId();
		assertEquals(CHECKING_ACCOUNT_ID, actual);
	}

	@Test
	public void correct_account_gets_money_when_deposit_through_bank() {
		bank.addAccount(CHECKING_ACCOUNT_ID, checkingAccount);

		bank.deposit(CHECKING_ACCOUNT_ID, DEPOSIT_AMOUNT);
		double actual = bank.getAccounts().get(CHECKING_ACCOUNT_ID).getBalance();

		assertEquals(DEPOSIT_AMOUNT, actual);
	}

	@Test
	public void other_accounts_not_get_money_when_deposit_into_an_account_through_bank() {
		bank.addAccount(CHECKING_ACCOUNT_ID, checkingAccount);
		bank.addAccount(CD_ACCOUNT_ID, cdAccount);

		bank.deposit(CHECKING_ACCOUNT_ID, DEPOSIT_AMOUNT);
		double actual = bank.getAccounts().get(CD_ACCOUNT_ID).getBalance();

		assertEquals(CD_STARTING_BALANCE, actual);
	}

	@Test
	public void correct_account_loses_money_when_withdraw_through_bank() {
		bank.addAccount(CHECKING_ACCOUNT_ID, checkingAccount);

		bank.deposit(CHECKING_ACCOUNT_ID, DEPOSIT_AMOUNT);
		bank.withdraw(CHECKING_ACCOUNT_ID, WITHDRAW_AMOUNT);

		double actual = bank.getAccounts().get(CHECKING_ACCOUNT_ID).getBalance();

		assertEquals(DEPOSIT_AMOUNT - WITHDRAW_AMOUNT, actual);
	}

	@Test
	public void other_accounts_not_lose_money_when_deposit_into_an_account_through_bank() {
		bank.addAccount(CHECKING_ACCOUNT_ID, checkingAccount);
		bank.addAccount(CD_ACCOUNT_ID, cdAccount);

		bank.deposit(CHECKING_ACCOUNT_ID, DEPOSIT_AMOUNT);
		bank.withdraw(CHECKING_ACCOUNT_ID, WITHDRAW_AMOUNT);

		double actual = bank.getAccounts().get(CD_ACCOUNT_ID).getBalance();

		assertEquals(CD_STARTING_BALANCE, actual);
	}

	@Test
	public void account_balance_cannot_go_below_0_after_withdrawing_through_bank() {
		bank.addAccount(CHECKING_ACCOUNT_ID, checkingAccount);

		bank.deposit(CHECKING_ACCOUNT_ID, DEPOSIT_AMOUNT);
		bank.withdraw(CHECKING_ACCOUNT_ID, DEPOSIT_AMOUNT + 1);

		double actual = bank.getAccounts().get(CHECKING_ACCOUNT_ID).getBalance();
		assertEquals(0, actual);
	}

	@Test
	public void deposit_twice_through_bank_works_as_expected() {
		bank.addAccount(CHECKING_ACCOUNT_ID, checkingAccount);

		bank.deposit(CHECKING_ACCOUNT_ID, DEPOSIT_AMOUNT);
		bank.deposit(CHECKING_ACCOUNT_ID, DEPOSIT_AMOUNT);

		double actual = bank.getAccounts().get(CHECKING_ACCOUNT_ID).getBalance();
		assertEquals(DEPOSIT_AMOUNT * 2, actual);
	}

	@Test
	public void withdraw_twice_through_bank_works_as_expected() {
		bank.addAccount(CHECKING_ACCOUNT_ID, checkingAccount);

		bank.deposit(CHECKING_ACCOUNT_ID, DEPOSIT_AMOUNT);
		bank.withdraw(CHECKING_ACCOUNT_ID, WITHDRAW_AMOUNT);
		bank.withdraw(CHECKING_ACCOUNT_ID, WITHDRAW_AMOUNT);

		double actual = bank.getAccounts().get(CHECKING_ACCOUNT_ID).getBalance();
		assertEquals(DEPOSIT_AMOUNT - WITHDRAW_AMOUNT * 2, actual);
	}

}
