import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CreateCommandHandlerTest {
	private static final String CREATE_ACTION = "Create";
	private static final String SAVINGS_ACCOUNT_TYPE = "savings";
	private static final String CHECKING_ACCOUNT_TYPE = "checking";
	private static final String CD_ACCOUNT_TYPE = "cd";
	private static final String DEFAULT_ACCOUNT_ID_1 = "12345678";
	private static final String DEFAULT_APR_STRING = "0.6";
	private static final double DEFAULT_APR_FLOAT = 0.6;
	private static final String ZERO_APR_STRING = "0";
	private static final String MAX_APR_STRING = "10";
	private static final String DEFAULT_BALANCE_STRING = "2000";
	private static final String MIN_CD_CREATE_BALANCE_STRING = "1000";
	private static final String MAX_CD_CREATE_BALANCE_STRING = "10000";

	CommandHandler commandHandler;
	Bank bank;

	@BeforeEach
	public void setUp() {
		bank = new Bank();
		commandHandler = new CommandHandler(bank);
	}

	@Test
	public void savings_account_with_correct_id_and_apr_is_created() {
		commandHandler.handle(
				CREATE_ACTION + " " + SAVINGS_ACCOUNT_TYPE + " " + DEFAULT_ACCOUNT_ID_1 + " " + DEFAULT_APR_STRING);
		assertEquals(DEFAULT_APR_FLOAT, bank.getAccounts().get(DEFAULT_ACCOUNT_ID_1).getAPR());
	}

	@Test
	public void checking_account_with_correct_id_and_apr_is_created() {
		commandHandler.handle(
				CREATE_ACTION + " " + CHECKING_ACCOUNT_TYPE + " " + DEFAULT_ACCOUNT_ID_1 + " " + DEFAULT_APR_STRING);
		assertEquals(DEFAULT_APR_FLOAT, bank.getAccounts().get(DEFAULT_ACCOUNT_ID_1).getAPR());
	}

	@Test
	public void cd_account_with_correct_id_and_apr_is_created() {
		commandHandler.handle(CREATE_ACTION + " " + CD_ACCOUNT_TYPE + " " + DEFAULT_ACCOUNT_ID_1 + " "
				+ DEFAULT_APR_STRING + " " + DEFAULT_BALANCE_STRING);
		assertEquals(DEFAULT_APR_FLOAT, bank.getAccounts().get(DEFAULT_ACCOUNT_ID_1).getAPR());
	}

	@Test
	public void savings_account_with_correct_id_and_zero_apr_is_created() {
		commandHandler.handle(
				CREATE_ACTION + " " + SAVINGS_ACCOUNT_TYPE + " " + DEFAULT_ACCOUNT_ID_1 + " " + ZERO_APR_STRING);
		assertEquals(0, bank.getAccounts().get(DEFAULT_ACCOUNT_ID_1).getAPR());
	}

	@Test
	public void checking_account_with_correct_id_and_zero_apr_is_created() {
		commandHandler.handle(
				CREATE_ACTION + " " + CHECKING_ACCOUNT_TYPE + " " + DEFAULT_ACCOUNT_ID_1 + " " + ZERO_APR_STRING);
		assertEquals(0, bank.getAccounts().get(DEFAULT_ACCOUNT_ID_1).getAPR());
	}

	@Test
	public void cd_account_with_correct_id_and_zero_apr_is_created() {
		commandHandler.handle(CREATE_ACTION + " " + CD_ACCOUNT_TYPE + " " + DEFAULT_ACCOUNT_ID_1 + " " + ZERO_APR_STRING
				+ " " + DEFAULT_BALANCE_STRING);
		assertEquals(0, bank.getAccounts().get(DEFAULT_ACCOUNT_ID_1).getAPR());
	}

	@Test
	public void savings_account_with_correct_id_and_max_apr_is_created() {
		commandHandler
				.handle(CREATE_ACTION + " " + SAVINGS_ACCOUNT_TYPE + " " + DEFAULT_ACCOUNT_ID_1 + " " + MAX_APR_STRING);
		assertEquals(10, bank.getAccounts().get(DEFAULT_ACCOUNT_ID_1).getAPR());
	}

	@Test
	public void checking_account_with_correct_id_and_max_apr_is_created() {
		commandHandler.handle(
				CREATE_ACTION + " " + CHECKING_ACCOUNT_TYPE + " " + DEFAULT_ACCOUNT_ID_1 + " " + MAX_APR_STRING);
		assertEquals(10, bank.getAccounts().get(DEFAULT_ACCOUNT_ID_1).getAPR());
	}

	@Test
	public void cd_account_with_correct_id_and_max_apr_is_created() {
		commandHandler.handle(CREATE_ACTION + " " + CD_ACCOUNT_TYPE + " " + DEFAULT_ACCOUNT_ID_1 + " " + MAX_APR_STRING
				+ " " + DEFAULT_BALANCE_STRING);
		assertEquals(10, bank.getAccounts().get(DEFAULT_ACCOUNT_ID_1).getAPR());
	}

	@Test
	public void savings_account_with_correct_id_and_balance_is_created() {
		commandHandler.handle(
				CREATE_ACTION + " " + SAVINGS_ACCOUNT_TYPE + " " + DEFAULT_ACCOUNT_ID_1 + " " + DEFAULT_APR_STRING);
		assertEquals(0, bank.getAccounts().get(DEFAULT_ACCOUNT_ID_1).getBalance());
	}

	@Test
	public void checking_account_with_correct_id_and_balance_is_created() {
		commandHandler.handle(
				CREATE_ACTION + " " + CHECKING_ACCOUNT_TYPE + " " + DEFAULT_ACCOUNT_ID_1 + " " + DEFAULT_APR_STRING);
		assertEquals(0, bank.getAccounts().get(DEFAULT_ACCOUNT_ID_1).getBalance());
	}

	@Test
	public void cd_account_with_correct_id_and_balance_is_created() {
		commandHandler.handle(CREATE_ACTION + " " + CD_ACCOUNT_TYPE + " " + DEFAULT_ACCOUNT_ID_1 + " "
				+ DEFAULT_APR_STRING + " " + DEFAULT_BALANCE_STRING);
		assertEquals(2000, bank.getAccounts().get(DEFAULT_ACCOUNT_ID_1).getBalance());
	}

	@Test
	public void cd_account_with_minimum_balance_is_created() {
		commandHandler.handle(CREATE_ACTION + " " + CD_ACCOUNT_TYPE + " " + DEFAULT_ACCOUNT_ID_1 + " "
				+ DEFAULT_APR_STRING + " " + MIN_CD_CREATE_BALANCE_STRING);
		assertEquals(1000, bank.getAccounts().get(DEFAULT_ACCOUNT_ID_1).getBalance());
	}

	@Test
	public void cd_account_with_maximum_balance_is_created() {
		commandHandler.handle(CREATE_ACTION + " " + CD_ACCOUNT_TYPE + " " + DEFAULT_ACCOUNT_ID_1 + " "
				+ DEFAULT_APR_STRING + " " + MAX_CD_CREATE_BALANCE_STRING);
		assertEquals(10000, bank.getAccounts().get(DEFAULT_ACCOUNT_ID_1).getBalance());
	}
}
