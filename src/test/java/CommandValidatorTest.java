import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CommandValidatorTest {
	private static final String CREATE_ACTION = "Create";
	private static final String SAVINGS_ACCOUNT_TYPE = "savings";
	private static final String DEFAULT_ACCOUNT_ID_1 = "12345678";
	private static final String DEFAULT_ACCOUNT_ID_2 = "98765432";
	private static final String DEFAULT_APR_STRING = "0.6";
	private static final double DEFAULT_APR_FLOAT = 0.6;
	private static final String ZERO_APR_STRING = "0";
	private static final String MAX_APR_STRING = "10";
	Bank bank;
	CommandValidator commandValidator;

	@BeforeEach
	public void setUp() {
		bank = new Bank();
		commandValidator = new CommandValidator(bank);
	}

	@Test
	public void savings_account_can_be_created_with_valid_parameters() {
		boolean actual = commandValidator.validate(
				CREATE_ACTION + " " + SAVINGS_ACCOUNT_TYPE + " " + DEFAULT_ACCOUNT_ID_1 + " " + DEFAULT_APR_STRING);
		assertTrue(actual);
	}

	@Test
	public void savings_account_can_be_created_with_valid_parameters_case_insensitive() {
		boolean actual = commandValidator.validate("CrEaTe sAvInGs " + DEFAULT_ACCOUNT_ID_1 + " " + DEFAULT_APR_STRING);
		assertTrue(actual);
	}

	@Test
	public void savings_account_can_be_created_with_valid_parameters_in_bank_with_other_accounts() {
		CheckingAccount checkingAccount = new CheckingAccount(DEFAULT_APR_FLOAT);
		bank.addAccount(DEFAULT_ACCOUNT_ID_2, checkingAccount);
		boolean actual = commandValidator.validate(
				CREATE_ACTION + " " + SAVINGS_ACCOUNT_TYPE + " " + DEFAULT_ACCOUNT_ID_1 + " " + DEFAULT_APR_STRING);
		assertTrue(actual);
	}

	@Test
	public void creating_savings_account_with_duplicate_id_is_invalid() {
		CheckingAccount checkingAccount = new CheckingAccount(DEFAULT_APR_FLOAT);
		bank.addAccount(DEFAULT_ACCOUNT_ID_1, checkingAccount);
		boolean actual = commandValidator.validate(
				CREATE_ACTION + " " + SAVINGS_ACCOUNT_TYPE + " " + DEFAULT_ACCOUNT_ID_1 + " " + DEFAULT_APR_STRING);
		assertFalse(actual);
	}

	@Test
	public void creating_savings_account_with_zero_apr_is_valid() {
		boolean actual = commandValidator.validate(
				CREATE_ACTION + " " + SAVINGS_ACCOUNT_TYPE + " " + DEFAULT_ACCOUNT_ID_1 + " " + ZERO_APR_STRING);
		assertTrue(actual);
	}

	@Test
	public void creating_savings_account_with_max_apr_is_valid() {
		boolean actual = commandValidator.validate(
				CREATE_ACTION + " " + SAVINGS_ACCOUNT_TYPE + " " + DEFAULT_ACCOUNT_ID_1 + " " + MAX_APR_STRING);
		assertTrue(actual);
	}

	@Test
	public void creating_savings_account_with_more_than_max_apr_is_invalid() {
		boolean actual = commandValidator.validate(
				CREATE_ACTION + " " + SAVINGS_ACCOUNT_TYPE + " " + DEFAULT_ACCOUNT_ID_1 + " " + MAX_APR_STRING + "1");
		assertFalse(actual);
	}

	@Test
	public void creating_savings_account_with_negative_apr_is_invalid() {
		boolean actual = commandValidator.validate(CREATE_ACTION + " " + SAVINGS_ACCOUNT_TYPE + " "
				+ DEFAULT_ACCOUNT_ID_1 + " " + "-" + DEFAULT_APR_STRING);
		assertFalse(actual);
	}

	@Test
	public void creating_savings_account_with_no_apr_is_invalid() {
		boolean actual = commandValidator
				.validate(CREATE_ACTION + " " + SAVINGS_ACCOUNT_TYPE + " " + DEFAULT_ACCOUNT_ID_1);
		assertFalse(actual);
	}
}
