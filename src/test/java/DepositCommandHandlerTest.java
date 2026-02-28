import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DepositCommandHandlerTest {
	CommandHandler commandHandler;
	Bank bank;

	private static final String CREATE_ACTION = "create";
	private static final String DEPOSIT_ACTION = "deposit";

	private static final String SAVINGS_ACCOUNT_TYPE = "savings";
	private static final String CHECKING_ACCOUNT_TYPE = "checking";

	private static final String DEFAULT_ACCOUNT_ID = "12345678";
	private static final String DEFAULT_ACCOUNT_ID_2 = "98765432";

	private static final String DEFAULT_APR = "1.0";

	private static final String DEPOSIT_100 = "100";
	private static final String DEPOSIT_200 = "200";
	private static final String DEPOSIT_500 = "500";
	private static final String DEPOSIT_1000 = "1000";
	private static final String DEPOSIT_ZERO = "0";

	@BeforeEach
	public void setUp() {
		bank = new Bank();
		commandHandler = new CommandHandler(bank);
	}

	@Test
	public void deposit_into_new_checking_account_sets_balance() {
		commandHandler
				.handle(CREATE_ACTION + " " + CHECKING_ACCOUNT_TYPE + " " + DEFAULT_ACCOUNT_ID + " " + DEFAULT_APR);

		commandHandler.handle(DEPOSIT_ACTION + " " + DEFAULT_ACCOUNT_ID + " " + DEPOSIT_100);

		assertEquals(100, bank.getAccounts().get(DEFAULT_ACCOUNT_ID).getBalance());
	}

	@Test
	public void deposit_adds_to_existing_checking_balance() {
		commandHandler
				.handle(CREATE_ACTION + " " + CHECKING_ACCOUNT_TYPE + " " + DEFAULT_ACCOUNT_ID + " " + DEFAULT_APR);

		commandHandler.handle(DEPOSIT_ACTION + " " + DEFAULT_ACCOUNT_ID + " " + DEPOSIT_200);

		commandHandler.handle(DEPOSIT_ACTION + " " + DEFAULT_ACCOUNT_ID + " " + DEPOSIT_100);

		assertEquals(300, bank.getAccounts().get(DEFAULT_ACCOUNT_ID).getBalance());
	}

	@Test
	public void deposit_into_savings_account_sets_balance() {
		commandHandler
				.handle(CREATE_ACTION + " " + SAVINGS_ACCOUNT_TYPE + " " + DEFAULT_ACCOUNT_ID + " " + DEFAULT_APR);

		commandHandler.handle(DEPOSIT_ACTION + " " + DEFAULT_ACCOUNT_ID + " " + DEPOSIT_500);

		assertEquals(500, bank.getAccounts().get(DEFAULT_ACCOUNT_ID).getBalance());
	}

	@Test
	public void deposit_adds_to_existing_savings_balance() {
		commandHandler
				.handle(CREATE_ACTION + " " + SAVINGS_ACCOUNT_TYPE + " " + DEFAULT_ACCOUNT_ID + " " + DEFAULT_APR);

		commandHandler.handle(DEPOSIT_ACTION + " " + DEFAULT_ACCOUNT_ID + " " + DEPOSIT_1000);

		commandHandler.handle(DEPOSIT_ACTION + " " + DEFAULT_ACCOUNT_ID + " " + DEPOSIT_500);

		assertEquals(1500, bank.getAccounts().get(DEFAULT_ACCOUNT_ID).getBalance());
	}

	@Test
	public void deposit_zero_does_not_change_balance() {
		commandHandler
				.handle(CREATE_ACTION + " " + CHECKING_ACCOUNT_TYPE + " " + DEFAULT_ACCOUNT_ID + " " + DEFAULT_APR);

		commandHandler.handle(DEPOSIT_ACTION + " " + DEFAULT_ACCOUNT_ID + " " + DEPOSIT_ZERO);

		assertEquals(0, bank.getAccounts().get(DEFAULT_ACCOUNT_ID).getBalance());
	}

	@Test
	public void deposit_into_one_account_does_not_change_balance_of_another() {
		commandHandler
				.handle(CREATE_ACTION + " " + CHECKING_ACCOUNT_TYPE + " " + DEFAULT_ACCOUNT_ID + " " + DEFAULT_APR);
		commandHandler
				.handle(CREATE_ACTION + " " + CHECKING_ACCOUNT_TYPE + " " + DEFAULT_ACCOUNT_ID_2 + " " + DEFAULT_APR);

		commandHandler.handle(DEPOSIT_ACTION + " " + DEFAULT_ACCOUNT_ID + " " + DEPOSIT_200);

		assertEquals(0, bank.getAccounts().get(DEFAULT_ACCOUNT_ID_2).getBalance());
	}

}
