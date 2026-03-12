package banking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class WithdrawHandlerTest {
	private static final int DEPOSIT_300 = 300;
	private static final int WITHDRAW_100 = 100;
	private static final double INITIAL_CD_BALANCE = 2000;
	private static final String WITHDRAW_300_STRING = "300";
	private static final String INITIAL_CD_BALANCE_STRING = "2000";
	CommandHandler commandHandler;
	Bank bank;

	private static final String CREATE_ACTION = "create";
	private static final String WITHDRAW_ACTION = "withdraw";

	private static final String SAVINGS_ACCOUNT_TYPE = "savings";
	private static final String CHECKING_ACCOUNT_TYPE = "checking";

	private static final String DEFAULT_ACCOUNT_ID = "12345678";
	private static final String DEFAULT_ACCOUNT_ID_2 = "98765432";

	private static final double DEFAULT_APR = 1.0;
	private static final String DEFAULT_APR_STRING = "1.0";

	private static final String WITHDRAW_100_STRING = "100";
	private static final String WITHDRAW_200 = "200";
	private static final String WITHDRAW_500 = "500";
	private static final String WITHDRAW_1000 = "1000";
	private static final String WITHDRAW_ZERO = "0";

	@BeforeEach
	public void setUp() {
		bank = new Bank();
		commandHandler = new CommandHandler(bank);
	}

	@Test
	public void withdraw_from_checking_account_reduces_balance() {
		CheckingAccount checkingAccount = new CheckingAccount(DEFAULT_APR);
		bank.addAccount(DEFAULT_ACCOUNT_ID, checkingAccount);
		bank.deposit(DEFAULT_ACCOUNT_ID, DEPOSIT_300);

		commandHandler.handle(WITHDRAW_ACTION + " " + DEFAULT_ACCOUNT_ID + " " + WITHDRAW_100_STRING);

		assertEquals(DEPOSIT_300 - WITHDRAW_100, bank.getAccounts().get(DEFAULT_ACCOUNT_ID).getBalance());
	}

	@Test
	public void withdraw_entire_balance_from_checking_sets_balance_to_zero() {
		CheckingAccount checkingAccount = new CheckingAccount(DEFAULT_APR);
		bank.addAccount(DEFAULT_ACCOUNT_ID, checkingAccount);
		bank.deposit(DEFAULT_ACCOUNT_ID, DEPOSIT_300);

		commandHandler.handle(WITHDRAW_ACTION + " " + DEFAULT_ACCOUNT_ID + " " + WITHDRAW_300_STRING);

		assertEquals(0, bank.getAccounts().get(DEFAULT_ACCOUNT_ID).getBalance());
	}

	@Test
	public void withdrawing_more_than_balance_sets_account_to_zero() {
		CheckingAccount checkingAccount = new CheckingAccount(DEFAULT_APR);
		bank.addAccount(DEFAULT_ACCOUNT_ID, checkingAccount);
		bank.deposit(DEFAULT_ACCOUNT_ID, DEPOSIT_300);

		commandHandler.handle(WITHDRAW_ACTION + " " + DEFAULT_ACCOUNT_ID + " " + WITHDRAW_300_STRING + ".1");

		assertEquals(0, bank.getAccounts().get(DEFAULT_ACCOUNT_ID).getBalance());
	}

	@Test
	public void withdrawing_zero_does_nothing() {
		CheckingAccount checkingAccount = new CheckingAccount(DEFAULT_APR);
		bank.addAccount(DEFAULT_ACCOUNT_ID, checkingAccount);
		bank.deposit(DEFAULT_ACCOUNT_ID, DEPOSIT_300);

		commandHandler.handle(WITHDRAW_ACTION + " " + DEFAULT_ACCOUNT_ID + " " + WITHDRAW_ZERO);

		assertEquals(DEPOSIT_300, bank.getAccounts().get(DEFAULT_ACCOUNT_ID).getBalance());
	}

	@Test
	public void withdraw_from_savings_account_reduces_balance() {
		SavingsAccount savingsAccount = new SavingsAccount(DEFAULT_APR);
		bank.addAccount(DEFAULT_ACCOUNT_ID, savingsAccount);
		bank.deposit(DEFAULT_ACCOUNT_ID, DEPOSIT_300);

		commandHandler.handle(WITHDRAW_ACTION + " " + DEFAULT_ACCOUNT_ID + " " + WITHDRAW_100_STRING);

		assertEquals(DEPOSIT_300 - WITHDRAW_100, bank.getAccounts().get(DEFAULT_ACCOUNT_ID).getBalance());
	}

//	@Test
//	public void savings_account_single_withdrawal_updates_balance() {
//		SavingsAccount savingsAccount = new SavingsAccount(DEFAULT_APR);
//		bank.addAccount(DEFAULT_ACCOUNT_ID, savingsAccount);
//		bank.deposit(DEFAULT_ACCOUNT_ID, DEPOSIT_300);
//
//		commandHandler.handle(WITHDRAW_ACTION + " " + DEFAULT_ACCOUNT_ID + " " + WITHDRAW_100_STRING);
//
//		assertFalse(bank.getAccounts().get(DEFAULT_ACCOUNT_ID).isWithdrawable());
//	}

//	@Test
//	public void withdraw_full_balance_from_cd_after_12_months() {
//		CDAccount cdAccount = new CDAccount(DEFAULT_APR, INITIAL_CD_BALANCE);
//		bank.addAccount(DEFAULT_ACCOUNT_ID, cdAccount);
//
//		bank.passTime(12);
//
//		commandHandler.handle(WITHDRAW_ACTION + " " + DEFAULT_ACCOUNT_ID + " " + INITIAL_CD_BALANCE_STRING);
//
//		assertEquals(0, bank.getAccounts().get(DEFAULT_ACCOUNT_ID).getBalance());
//	}
//
//	@Test
//	public void cd_withdrawal_greater_than_balance_sets_balance_to_zero() {
//		CDAccount cdAccount = new CDAccount(DEFAULT_APR, INITIAL_CD_BALANCE);
//		bank.addAccount(DEFAULT_ACCOUNT_ID, cdAccount);
//
//		bank.passTime(12);
//
//		commandHandler.handle(WITHDRAW_ACTION + " " + DEFAULT_ACCOUNT_ID + " " + WITHDRAW_LARGE_STRING);
//
//		assertEquals(0, bank.getAccounts().get(DEFAULT_ACCOUNT_ID).getBalance());
//	}

}
