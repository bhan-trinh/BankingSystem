package banking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PassTimeHandlerTest {
	private static final String PASS_TIME_ACTION = "Pass";
	private static final String PASS_1_MONTH = "1";
	private static final String PASS_2_MONTHS = "2";

	private static final String DEFAULT_ACCOUNT_ID = "12345678";

	private static final double DEFAULT_APR = 1.0;
	private static final double ZERO_APR = 0;

	private static final int ZERO_BALANCE = 0;
	private static final double BALANCE_99_99 = 99.99;
	private static final double ACCOUNT_MONTHLY_FEE = 25;
	private static final int DEFAULT_ACCOUNT_BALANCE = 1000;
	private static final int MIN_ACCOUNT_BALANCE_NOT_DEDUCT_FEE = 100;
	private static final int BELOW_DEDUCT_FEE_BALANCE = 10;
	private static final int DEDUCT_FEE_BALANCE = 25;

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

		commandHandler.handle(PASS_TIME_ACTION + " " + PASS_1_MONTH);

		assertFalse(bank.checkAccountExistsById(DEFAULT_ACCOUNT_ID));
	}

	@Test
	public void pass_month_closes_empty_checking_account() {
		CheckingAccount account = new CheckingAccount(DEFAULT_APR);
		bank.addAccount(DEFAULT_ACCOUNT_ID, account);

		commandHandler.handle(PASS_TIME_ACTION + " " + PASS_1_MONTH);

		assertFalse(bank.checkAccountExistsById(DEFAULT_ACCOUNT_ID));
	}

	@Test
	public void pass_month_closes_empty_cd_account() {
		CDAccount cdAccount = new CDAccount(DEFAULT_APR, ZERO_BALANCE);
		bank.addAccount(DEFAULT_ACCOUNT_ID, cdAccount);

		commandHandler.handle(PASS_TIME_ACTION + " " + PASS_1_MONTH);

		assertFalse(bank.checkAccountExistsById(DEFAULT_ACCOUNT_ID));
	}

	@Test
	public void pass_month_deducts_fee_from_savings_account_with_99_99() {
		SavingsAccount savingsAccount = new SavingsAccount(ZERO_APR);
		bank.addAccount(DEFAULT_ACCOUNT_ID, savingsAccount);
		bank.deposit(DEFAULT_ACCOUNT_ID, BALANCE_99_99);

		commandHandler.handle(PASS_TIME_ACTION + " " + PASS_1_MONTH);

		assertEquals(BALANCE_99_99 - ACCOUNT_MONTHLY_FEE, bank.getAccounts().get(DEFAULT_ACCOUNT_ID).getBalance());
	}

	@Test
	public void pass_month_deducts_fee_from_checking_account_with_99_99() {
		CheckingAccount checkingAccount = new CheckingAccount(ZERO_APR);
		bank.addAccount(DEFAULT_ACCOUNT_ID, checkingAccount);
		bank.deposit(DEFAULT_ACCOUNT_ID, BALANCE_99_99);

		commandHandler.handle(PASS_TIME_ACTION + " " + PASS_1_MONTH);

		assertEquals(BALANCE_99_99 - ACCOUNT_MONTHLY_FEE, bank.getAccounts().get(DEFAULT_ACCOUNT_ID).getBalance());
	}

	@Test
	public void pass_month_deducts_fee_from_cd_account_with_99_99() {
		CDAccount cdAccount = new CDAccount(ZERO_APR, BALANCE_99_99);
		bank.addAccount(DEFAULT_ACCOUNT_ID, cdAccount);

		commandHandler.handle(PASS_TIME_ACTION + " " + PASS_1_MONTH);

		assertEquals(BALANCE_99_99 - ACCOUNT_MONTHLY_FEE, bank.getAccounts().get(DEFAULT_ACCOUNT_ID).getBalance());
	}

	@Test
	public void pass_month_does_not_deduct_fee_when_balance_is_100() {
		CheckingAccount checkingAccount = new CheckingAccount(ZERO_APR);
		bank.addAccount(DEFAULT_ACCOUNT_ID, checkingAccount);
		bank.deposit(DEFAULT_ACCOUNT_ID, MIN_ACCOUNT_BALANCE_NOT_DEDUCT_FEE);

		commandHandler.handle(PASS_TIME_ACTION + " " + PASS_1_MONTH);

		assertEquals(MIN_ACCOUNT_BALANCE_NOT_DEDUCT_FEE, bank.getAccounts().get(DEFAULT_ACCOUNT_ID).getBalance());
	}

	@Test
	public void pass_month_deduction_does_not_drop_balance_below_zero() {
		SavingsAccount savingsAccount = new SavingsAccount(DEFAULT_APR);
		bank.addAccount(DEFAULT_ACCOUNT_ID, savingsAccount);
		bank.deposit(DEFAULT_ACCOUNT_ID, BELOW_DEDUCT_FEE_BALANCE);

		commandHandler.handle(PASS_TIME_ACTION + " " + PASS_1_MONTH);

		assertEquals(ZERO_BALANCE, bank.getAccounts().get(DEFAULT_ACCOUNT_ID).getBalance());
	}

	@Test
	public void pass_two_months_deduct_then_close_account() {
		SavingsAccount savingsAccount = new SavingsAccount(DEFAULT_APR);
		bank.addAccount(DEFAULT_ACCOUNT_ID, savingsAccount);
		bank.deposit(DEFAULT_ACCOUNT_ID, DEDUCT_FEE_BALANCE);

		commandHandler.handle(PASS_TIME_ACTION + " " + PASS_2_MONTHS);

		assertFalse(bank.checkAccountExistsById(DEFAULT_ACCOUNT_ID));
	}

	@Test
	public void pass_month_calculates_apr_for_savings_account() {
		SavingsAccount savingsAccount = new SavingsAccount(DEFAULT_APR);
		bank.addAccount(DEFAULT_ACCOUNT_ID, savingsAccount);
		bank.deposit(DEFAULT_ACCOUNT_ID, DEFAULT_ACCOUNT_BALANCE);

		commandHandler.handle(PASS_TIME_ACTION + " " + PASS_1_MONTH);
		assertEquals(DEFAULT_ACCOUNT_BALANCE + calculateRateFromApr() * DEFAULT_ACCOUNT_BALANCE,
				bank.getAccounts().get(DEFAULT_ACCOUNT_ID).getBalance());
	}

	@Test
	public void pass_month_calculates_apr_for_checking_account() {
		CheckingAccount checkingAccount = new CheckingAccount(DEFAULT_APR);
		bank.addAccount(DEFAULT_ACCOUNT_ID, checkingAccount);
		bank.deposit(DEFAULT_ACCOUNT_ID, DEFAULT_ACCOUNT_BALANCE);

		commandHandler.handle(PASS_TIME_ACTION + " " + PASS_1_MONTH);

		assertEquals(DEFAULT_ACCOUNT_BALANCE + calculateRateFromApr() * DEFAULT_ACCOUNT_BALANCE,
				bank.getAccounts().get(DEFAULT_ACCOUNT_ID).getBalance());
	}

	@Test
	public void pass_month_calculates_apr_for_cd_account() {
		CDAccount cdAccount = new CDAccount(DEFAULT_APR, DEFAULT_ACCOUNT_BALANCE);
		bank.addAccount(DEFAULT_ACCOUNT_ID, cdAccount);

		commandHandler.handle(PASS_TIME_ACTION + " " + PASS_1_MONTH);

		double correctAmount = DEFAULT_ACCOUNT_BALANCE;
		double rate = calculateRateFromApr();
		for (int i = 0; i < 4; i++) {
			correctAmount += rate * correctAmount;
		}

		assertEquals(correctAmount, bank.getAccounts().get(DEFAULT_ACCOUNT_ID).getBalance());

	}

	private static double calculateRateFromApr() {
		return DEFAULT_APR / 100 / 12;
	}
}
