package banking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PassTimeHandlerTest {
	private static final String DEFAULT_ACCOUNT_ID = "12345678";
	private static final String DEFAULT_ACCOUNT_ID_2 = "98765432";

	private static final double DEFAULT_APR = 1.0;
	private static final String PASS_TIME_ACTION = "Pass";
	private static final double ZERO_APR = 0;

	CommandHandler commandHandler;
	Bank bank;

	@BeforeEach
	public void setUp() {
		bank = new Bank();
		commandHandler = new CommandHandler(bank);
	}

	@Test
	public void pass_month_closes_empty_savings_account() {
		SavingsAccount account = new SavingsAccount(DEFAULT_APR);
		bank.addAccount(DEFAULT_ACCOUNT_ID, account);

		commandHandler.handle(PASS_TIME_ACTION + " 1");

		assertFalse(bank.checkAccountExistsById(DEFAULT_ACCOUNT_ID));
	}

	@Test
	public void pass_month_closes_empty_checking_account() {
		CheckingAccount account = new CheckingAccount(DEFAULT_APR);
		bank.addAccount(DEFAULT_ACCOUNT_ID, account);

		commandHandler.handle(PASS_TIME_ACTION + " 1");

		assertFalse(bank.checkAccountExistsById(DEFAULT_ACCOUNT_ID));
	}

	@Test
	public void pass_month_closes_empty_cd_account() {
		CDAccount cdAccount = new CDAccount(DEFAULT_APR, 0);
		bank.addAccount(DEFAULT_ACCOUNT_ID, cdAccount);

		commandHandler.handle(PASS_TIME_ACTION + " 1");

		assertFalse(bank.checkAccountExistsById(DEFAULT_ACCOUNT_ID));
	}

	@Test
	public void pass_month_deducts_fee_from_savings_account_with_99_99() {
		SavingsAccount savingsAccount = new SavingsAccount(ZERO_APR);
		bank.addAccount(DEFAULT_ACCOUNT_ID, savingsAccount);
		bank.deposit(DEFAULT_ACCOUNT_ID, 99.99);

		commandHandler.handle(PASS_TIME_ACTION + " 1");

		assertEquals(74.99, bank.getAccounts().get(DEFAULT_ACCOUNT_ID).getBalance());
	}

	@Test
	public void pass_month_deducts_fee_from_checking_account_with_99_99() {
		CheckingAccount checkingAccount = new CheckingAccount(ZERO_APR);
		bank.addAccount(DEFAULT_ACCOUNT_ID, checkingAccount);
		bank.deposit(DEFAULT_ACCOUNT_ID, 99.99);

		commandHandler.handle(PASS_TIME_ACTION + " 1");

		assertEquals(74.99, bank.getAccounts().get(DEFAULT_ACCOUNT_ID).getBalance());
	}

	@Test
	public void pass_month_deducts_fee_from_cd_account_with_99_99() {
		CDAccount cdAccount = new CDAccount(ZERO_APR, 99.99);
		bank.addAccount(DEFAULT_ACCOUNT_ID, cdAccount);

		commandHandler.handle(PASS_TIME_ACTION + " 1");

		assertEquals(74.99, bank.getAccounts().get(DEFAULT_ACCOUNT_ID).getBalance());
	}

	@Test
	public void pass_month_does_not_deduct_fee_when_balance_is_100() {
		CheckingAccount checkingAccount = new CheckingAccount(ZERO_APR);
		bank.addAccount(DEFAULT_ACCOUNT_ID, checkingAccount);
		bank.deposit(DEFAULT_ACCOUNT_ID, 100);

		commandHandler.handle(PASS_TIME_ACTION + " 1");

		assertEquals(100, bank.getAccounts().get(DEFAULT_ACCOUNT_ID).getBalance());
	}

	@Test
	public void pass_month_deduction_does_not_drop_balance_below_zero() {
		SavingsAccount savingsAccount = new SavingsAccount(DEFAULT_APR);
		bank.addAccount(DEFAULT_ACCOUNT_ID, savingsAccount);
		bank.deposit(DEFAULT_ACCOUNT_ID, 10);

		commandHandler.handle(PASS_TIME_ACTION + " 1");

		assertEquals(0, bank.getAccounts().get(DEFAULT_ACCOUNT_ID).getBalance());
	}

	@Test
	public void pass_two_months_deduct_then_close_account() {
		SavingsAccount savingsAccount = new SavingsAccount(DEFAULT_APR);
		bank.addAccount(DEFAULT_ACCOUNT_ID, savingsAccount);
		bank.deposit(DEFAULT_ACCOUNT_ID, 25);

		commandHandler.handle(PASS_TIME_ACTION + " 2");

		assertFalse(bank.checkAccountExistsById(DEFAULT_ACCOUNT_ID));
	}

	@Test
	public void pass_month_calculates_apr_for_savings_account() {
		SavingsAccount savingsAccount = new SavingsAccount(DEFAULT_APR);
		bank.addAccount(DEFAULT_ACCOUNT_ID, savingsAccount);
		bank.deposit(DEFAULT_ACCOUNT_ID, 1000);

		commandHandler.handle(PASS_TIME_ACTION + " 1");
		assertEquals(1000 + DEFAULT_APR / 100 / 12 * 1000, bank.getAccounts().get(DEFAULT_ACCOUNT_ID).getBalance());
	}

	@Test
	public void pass_month_calculates_apr_for_checking_account() {
		CheckingAccount checkingAccount = new CheckingAccount(DEFAULT_APR);
		bank.addAccount(DEFAULT_ACCOUNT_ID, checkingAccount);
		bank.deposit(DEFAULT_ACCOUNT_ID, 1000);

		commandHandler.handle(PASS_TIME_ACTION + " 1");

		assertEquals(1000 + DEFAULT_APR / 100 / 12 * 1000, bank.getAccounts().get(DEFAULT_ACCOUNT_ID).getBalance());
	}

	@Test
	public void pass_month_calculates_apr_for_cd_account() {
		CDAccount cdAccount = new CDAccount(DEFAULT_APR, 1000);
		bank.addAccount(DEFAULT_ACCOUNT_ID, cdAccount);

		commandHandler.handle(PASS_TIME_ACTION + " 1");

		double correctAmount = 1000;
		double rate = DEFAULT_APR / 100 / 12;
		for (int i = 0; i < 4; i++) {
			correctAmount += rate * correctAmount;
		}

		assertEquals(correctAmount, bank.getAccounts().get(DEFAULT_ACCOUNT_ID).getBalance());

	}
}
