package banking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CreateValidatorTest {
	private static final String CREATE_ACTION = "Create";
	private static final String SAVINGS_ACCOUNT_TYPE = "savings";
	private static final String CHECKING_ACCOUNT_TYPE = "checking";
	private static final String CD_ACCOUNT_TYPE = "cd";
	private static final String DEFAULT_ACCOUNT_ID_1 = "12345678";
	private static final String DEFAULT_ACCOUNT_ID_2 = "98765432";
	private static final String DEFAULT_APR_STRING = "0.6";
	private static final double DEFAULT_APR_FLOAT = 0.6;
	private static final String ZERO_APR_STRING = "0";
	private static final String MAX_APR_STRING = "10";
	private static final String DEFAULT_BALANCE_STRING = "2000";
	private static final String ZERO_BALANCE_STRING = "0";
	private static final String ONE_BALANCE_STRING = "1";
	private static final String MIN_CD_CREATE_BALANCE_STRING = "1000";
	private static final String MAX_CD_CREATE_BALANCE_STRING = "10000";
	Bank bank;
	CommandValidator commandValidator;

	@BeforeEach
	public void setUp() {
		bank = new Bank();
		commandValidator = new CommandValidator(bank);
	}

	// ===== GENERAL =====
	@Test
	public void creating_savings_account_with_valid_parameters_is_valid() {
		boolean actual = commandValidator.validate(
				CREATE_ACTION + " " + SAVINGS_ACCOUNT_TYPE + " " + DEFAULT_ACCOUNT_ID_1 + " " + DEFAULT_APR_STRING);
		assertTrue(actual);
	}

	@Test
	public void creating_checking_account_with_valid_parameters_is_valid() {
		boolean actual = commandValidator.validate(
				CREATE_ACTION + " " + CHECKING_ACCOUNT_TYPE + " " + DEFAULT_ACCOUNT_ID_1 + " " + DEFAULT_APR_STRING);
		assertTrue(actual);
	}

	@Test
	public void creating_cd_account_with_valid_parameters_is_valid() {
		boolean actual = commandValidator.validate(CREATE_ACTION + " " + CD_ACCOUNT_TYPE + " " + DEFAULT_ACCOUNT_ID_1
				+ " " + DEFAULT_APR_STRING + " " + DEFAULT_BALANCE_STRING);
		assertTrue(actual);
	}

	@Test
	public void creating_savings_account_with_valid_parameters_case_insensitive_is_valid() {
		boolean actual = commandValidator.validate("CrEaTe sAvInGs " + DEFAULT_ACCOUNT_ID_1 + " " + DEFAULT_APR_STRING);
		assertTrue(actual);
	}

	@Test
	public void creating_checking_account_with_valid_parameters_case_insensitive_is_valid() {
		boolean actual = commandValidator
				.validate("CrEaTe chEcKing " + DEFAULT_ACCOUNT_ID_1 + " " + DEFAULT_APR_STRING);
		assertTrue(actual);
	}

	@Test
	public void creating_cd_account_with_valid_parameters_case_insensitive_is_valid() {
		boolean actual = commandValidator.validate(
				"CrEaTe cD " + DEFAULT_ACCOUNT_ID_1 + " " + DEFAULT_APR_STRING + " " + DEFAULT_BALANCE_STRING);
		assertTrue(actual);
	}

	@Test
	public void creating_savings_account_with_valid_parameters_in_bank_with_other_accounts_is_valid() {
		CheckingAccount checkingAccount = new CheckingAccount(DEFAULT_APR_FLOAT);
		bank.addAccount(DEFAULT_ACCOUNT_ID_2, checkingAccount);
		boolean actual = commandValidator.validate(
				CREATE_ACTION + " " + SAVINGS_ACCOUNT_TYPE + " " + DEFAULT_ACCOUNT_ID_1 + " " + DEFAULT_APR_STRING);
		assertTrue(actual);
	}

	@Test
	public void creating_checking_account_with_valid_parameters_in_bank_with_other_accounts_is_valid() {
		CheckingAccount checkingAccount = new CheckingAccount(DEFAULT_APR_FLOAT);
		bank.addAccount(DEFAULT_ACCOUNT_ID_2, checkingAccount);
		boolean actual = commandValidator.validate(
				CREATE_ACTION + " " + CHECKING_ACCOUNT_TYPE + " " + DEFAULT_ACCOUNT_ID_1 + " " + DEFAULT_APR_STRING);
		assertTrue(actual);
	}

	@Test
	public void creating_cd_account_with_valid_parameters_in_bank_with_other_accounts_is_valid() {
		CheckingAccount checkingAccount = new CheckingAccount(DEFAULT_APR_FLOAT);
		bank.addAccount(DEFAULT_ACCOUNT_ID_2, checkingAccount);
		boolean actual = commandValidator.validate(CREATE_ACTION + " " + CD_ACCOUNT_TYPE + " " + DEFAULT_ACCOUNT_ID_1
				+ " " + DEFAULT_APR_STRING + " " + DEFAULT_BALANCE_STRING);
		assertTrue(actual);
	}

	@Test
	public void creating_account_with_no_parameters_is_invalid() {
		boolean actual = commandValidator.validate(CREATE_ACTION + " " + SAVINGS_ACCOUNT_TYPE);
		assertFalse(actual);
	}

	@Test
	public void creating_with_no_parameters_is_invalid() {
		boolean actual = commandValidator.validate(CREATE_ACTION);
		assertFalse(actual);
	}

	@Test
	public void creating_with_empty_parameters_is_invalid() {
		boolean actual = commandValidator.validate(CREATE_ACTION + "      ");
		assertFalse(actual);
	}

	@Test
	public void creating_with_parameters_in_wrong_order_is_invalid() {
		boolean actual = commandValidator.validate(
				CREATE_ACTION + " " + DEFAULT_APR_STRING + " " + SAVINGS_ACCOUNT_TYPE + " " + DEFAULT_ACCOUNT_ID_1);
		assertFalse(actual);
	}

	@Test
	public void creating_with_parameters_in_wrong_order_is_invalid_2() {
		boolean actual = commandValidator.validate(
				CREATE_ACTION + " " + DEFAULT_ACCOUNT_ID_1 + " " + DEFAULT_APR_STRING + " " + SAVINGS_ACCOUNT_TYPE);
		assertFalse(actual);
	}

	@Test
	public void creating_with_parameters_in_wrong_order_is_invalid_3() {
		boolean actual = commandValidator.validate(
				DEFAULT_APR_STRING + " " + SAVINGS_ACCOUNT_TYPE + " " + CREATE_ACTION + " " + DEFAULT_ACCOUNT_ID_1);
		assertFalse(actual);
	}

	@Test
	public void creating_some_unknown_account_type_is_invalid() {
		boolean actual = commandValidator
				.validate(CREATE_ACTION + " " + "unknown" + " " + DEFAULT_ACCOUNT_ID_1 + " " + DEFAULT_APR_STRING);
		assertFalse(actual);
	}

	// ===== ID =====
	@Test
	public void creating_savings_account_with_duplicate_id_is_invalid() {
		CheckingAccount checkingAccount = new CheckingAccount(DEFAULT_APR_FLOAT);
		bank.addAccount(DEFAULT_ACCOUNT_ID_1, checkingAccount);
		boolean actual = commandValidator.validate(
				CREATE_ACTION + " " + SAVINGS_ACCOUNT_TYPE + " " + DEFAULT_ACCOUNT_ID_1 + " " + DEFAULT_APR_STRING);
		assertFalse(actual);
	}

	@Test
	public void creating_checking_account_with_duplicate_id_is_invalid() {
		CheckingAccount checkingAccount = new CheckingAccount(DEFAULT_APR_FLOAT);
		bank.addAccount(DEFAULT_ACCOUNT_ID_1, checkingAccount);
		boolean actual = commandValidator.validate(
				CREATE_ACTION + " " + CHECKING_ACCOUNT_TYPE + " " + DEFAULT_ACCOUNT_ID_1 + " " + DEFAULT_APR_STRING);
		assertFalse(actual);
	}

	@Test
	public void creating_cd_account_with_duplicate_id_is_invalid() {
		CheckingAccount checkingAccount = new CheckingAccount(DEFAULT_APR_FLOAT);
		bank.addAccount(DEFAULT_ACCOUNT_ID_1, checkingAccount);
		boolean actual = commandValidator.validate(
				CREATE_ACTION + " " + CD_ACCOUNT_TYPE + " " + DEFAULT_ACCOUNT_ID_1 + " " + DEFAULT_APR_STRING);
		assertFalse(actual);
	}

	@Test
	public void creating_savings_account_with_non_numeric_id_is_invalid() {
		boolean actual = commandValidator
				.validate(CREATE_ACTION + " " + SAVINGS_ACCOUNT_TYPE + " " + "12345abc" + " " + DEFAULT_APR_STRING);
		assertFalse(actual);
	}

	@Test
	public void creating_checking_account_with_non_numeric_id_is_invalid() {
		boolean actual = commandValidator
				.validate(CREATE_ACTION + " " + CHECKING_ACCOUNT_TYPE + " " + "12345abc" + " " + DEFAULT_APR_STRING);
		assertFalse(actual);
	}

	@Test
	public void creating_cd_account_with_non_numeric_id_is_invalid() {
		boolean actual = commandValidator
				.validate(CREATE_ACTION + " " + CD_ACCOUNT_TYPE + " " + "12345abc" + " " + DEFAULT_APR_STRING);
		assertFalse(actual);
	}

	@Test
	public void creating_savings_account_with_less_than_8_digits_id_is_invalid() {
		boolean actual = commandValidator
				.validate(CREATE_ACTION + " " + SAVINGS_ACCOUNT_TYPE + " " + "1234567" + " " + DEFAULT_APR_STRING);
		assertFalse(actual);
	}

	@Test
	public void creating_checking_account_with_less_than_8_digits_id_is_invalid() {
		boolean actual = commandValidator
				.validate(CREATE_ACTION + " " + CHECKING_ACCOUNT_TYPE + " " + "1234567" + " " + DEFAULT_APR_STRING);
		assertFalse(actual);
	}

	@Test
	public void creating_cd_account_with_less_than_8_digits_id_is_invalid() {
		boolean actual = commandValidator
				.validate(CREATE_ACTION + " " + CD_ACCOUNT_TYPE + " " + "1234567" + " " + DEFAULT_APR_STRING);
		assertFalse(actual);
	}

	@Test
	public void creating_savings_account_with_more_than_8_digits_id_is_invalid() {
		boolean actual = commandValidator
				.validate(CREATE_ACTION + " " + SAVINGS_ACCOUNT_TYPE + " " + "123456789" + " " + DEFAULT_APR_STRING);
		assertFalse(actual);
	}

	@Test
	public void creating_checking_account_with_more_than_8_digits_id_is_invalid() {
		boolean actual = commandValidator
				.validate(CREATE_ACTION + " " + CHECKING_ACCOUNT_TYPE + " " + "123456789" + " " + DEFAULT_APR_STRING);
		assertFalse(actual);
	}

	@Test
	public void creating_cd_account_with_more_than_8_digits_id_is_invalid() {
		boolean actual = commandValidator
				.validate(CREATE_ACTION + " " + CD_ACCOUNT_TYPE + " " + "123456789" + " " + DEFAULT_APR_STRING);
		assertFalse(actual);
	}

	// ===== APR =====
	@Test
	public void creating_savings_account_with_zero_apr_is_valid() {
		boolean actual = commandValidator.validate(
				CREATE_ACTION + " " + SAVINGS_ACCOUNT_TYPE + " " + DEFAULT_ACCOUNT_ID_1 + " " + ZERO_APR_STRING);
		assertTrue(actual);
	}

	@Test
	public void creating_checking_account_with_zero_apr_is_valid() {
		boolean actual = commandValidator.validate(
				CREATE_ACTION + " " + CHECKING_ACCOUNT_TYPE + " " + DEFAULT_ACCOUNT_ID_1 + " " + ZERO_APR_STRING);
		assertTrue(actual);
	}

	@Test
	public void creating_cd_account_with_zero_apr_is_valid() {
		boolean actual = commandValidator.validate(CREATE_ACTION + " " + CD_ACCOUNT_TYPE + " " + DEFAULT_ACCOUNT_ID_1
				+ " " + ZERO_APR_STRING + " " + DEFAULT_BALANCE_STRING);
		assertTrue(actual);
	}

	@Test
	public void creating_savings_account_with_max_apr_is_valid() {
		boolean actual = commandValidator.validate(
				CREATE_ACTION + " " + SAVINGS_ACCOUNT_TYPE + " " + DEFAULT_ACCOUNT_ID_1 + " " + MAX_APR_STRING);
		assertTrue(actual);
	}

	@Test
	public void creating_checking_account_with_max_apr_is_valid() {
		boolean actual = commandValidator.validate(
				CREATE_ACTION + " " + CHECKING_ACCOUNT_TYPE + " " + DEFAULT_ACCOUNT_ID_1 + " " + MAX_APR_STRING);
		assertTrue(actual);
	}

	@Test
	public void creating_cd_account_with_max_apr_is_valid() {
		boolean actual = commandValidator.validate(CREATE_ACTION + " " + CD_ACCOUNT_TYPE + " " + DEFAULT_ACCOUNT_ID_1
				+ " " + MAX_APR_STRING + " " + DEFAULT_BALANCE_STRING);
		assertTrue(actual);
	}

	@Test
	public void creating_savings_account_with_more_than_max_apr_is_invalid() {
		boolean actual = commandValidator.validate(CREATE_ACTION + " " + SAVINGS_ACCOUNT_TYPE + " "
				+ DEFAULT_ACCOUNT_ID_1 + " " + MAX_APR_STRING + ONE_BALANCE_STRING);
		assertFalse(actual);
	}

	@Test
	public void creating_checking_account_with_more_than_max_apr_is_invalid() {
		boolean actual = commandValidator.validate(CREATE_ACTION + " " + CHECKING_ACCOUNT_TYPE + " "
				+ DEFAULT_ACCOUNT_ID_1 + " " + MAX_APR_STRING + ONE_BALANCE_STRING);
		assertFalse(actual);
	}

	@Test
	public void creating_cd_account_with_more_than_max_apr_is_invalid() {
		boolean actual = commandValidator.validate(CREATE_ACTION + " " + CD_ACCOUNT_TYPE + " " + DEFAULT_ACCOUNT_ID_1
				+ " " + MAX_APR_STRING + ONE_BALANCE_STRING + " " + DEFAULT_BALANCE_STRING);
		assertFalse(actual);
	}

	@Test
	public void creating_savings_account_with_negative_apr_is_invalid() {
		boolean actual = commandValidator.validate(CREATE_ACTION + " " + SAVINGS_ACCOUNT_TYPE + " "
				+ DEFAULT_ACCOUNT_ID_1 + " " + "-" + DEFAULT_APR_STRING);
		assertFalse(actual);
	}

	@Test
	public void creating_checking_account_with_negative_apr_is_invalid() {
		boolean actual = commandValidator.validate(CREATE_ACTION + " " + CHECKING_ACCOUNT_TYPE + " "
				+ DEFAULT_ACCOUNT_ID_1 + " " + "-" + DEFAULT_APR_STRING);
		assertFalse(actual);
	}

	@Test
	public void creating_cd_account_with_negative_apr_is_invalid() {
		boolean actual = commandValidator.validate(CREATE_ACTION + " " + CD_ACCOUNT_TYPE + " " + DEFAULT_ACCOUNT_ID_1
				+ " " + "-" + DEFAULT_APR_STRING + " " + DEFAULT_BALANCE_STRING);
		assertFalse(actual);
	}

	@Test
	public void creating_savings_account_with_no_apr_is_invalid() {
		boolean actual = commandValidator
				.validate(CREATE_ACTION + " " + SAVINGS_ACCOUNT_TYPE + " " + DEFAULT_ACCOUNT_ID_1);
		assertFalse(actual);
	}

	@Test
	public void creating_checking_account_with_no_apr_is_invalid() {
		boolean actual = commandValidator
				.validate(CREATE_ACTION + " " + CHECKING_ACCOUNT_TYPE + " " + DEFAULT_ACCOUNT_ID_1);
		assertFalse(actual);
	}

	@Test
	public void creating_cd_account_with_no_apr_is_invalid() {
		boolean actual = commandValidator.validate(
				CREATE_ACTION + " " + CD_ACCOUNT_TYPE + " " + DEFAULT_ACCOUNT_ID_1 + " " + DEFAULT_BALANCE_STRING);
		assertFalse(actual);
	}

	// ===== BALANCE =====

	@Test
	public void creating_savings_account_with_account_balance_is_invalid() {
		boolean actual = commandValidator.validate(CREATE_ACTION + " " + SAVINGS_ACCOUNT_TYPE + " "
				+ DEFAULT_ACCOUNT_ID_1 + " " + DEFAULT_APR_STRING + " " + DEFAULT_BALANCE_STRING);
		assertFalse(actual);
	}

	@Test
	public void creating_checking_account_with_account_balance_is_invalid() {
		boolean actual = commandValidator.validate(CREATE_ACTION + " " + CHECKING_ACCOUNT_TYPE + " "
				+ DEFAULT_ACCOUNT_ID_1 + " " + DEFAULT_APR_STRING + " " + DEFAULT_BALANCE_STRING);
		assertFalse(actual);
	}

	@Test
	public void creating_savings_account_with_0_balance_is_invalid() {
		boolean actual = commandValidator.validate(CREATE_ACTION + " " + SAVINGS_ACCOUNT_TYPE + " "
				+ DEFAULT_ACCOUNT_ID_1 + " " + DEFAULT_APR_STRING + " " + ZERO_BALANCE_STRING);
		assertFalse(actual);
	}

	@Test
	public void creating_checking_account_with_0_balance_is_invalid() {
		boolean actual = commandValidator.validate(CREATE_ACTION + " " + CHECKING_ACCOUNT_TYPE + " "
				+ DEFAULT_ACCOUNT_ID_1 + " " + DEFAULT_APR_STRING + " " + ZERO_BALANCE_STRING);
		assertFalse(actual);
	}

	@Test
	public void creating_cd_account_with_0_balance_is_invalid() {
		boolean actual = commandValidator.validate(CREATE_ACTION + " " + CD_ACCOUNT_TYPE + " " + DEFAULT_ACCOUNT_ID_1
				+ " " + DEFAULT_APR_STRING + " " + ZERO_BALANCE_STRING);
		assertFalse(actual);
	}

	@Test
	public void creating_cd_account_with_1_balance_is_invalid() {
		boolean actual = commandValidator.validate(CREATE_ACTION + " " + CD_ACCOUNT_TYPE + " " + DEFAULT_ACCOUNT_ID_1
				+ " " + DEFAULT_APR_STRING + " " + ONE_BALANCE_STRING);
		assertFalse(actual);
	}

	@Test
	public void creating_cd_account_with_minimum_balance_is_valid() {
		boolean actual = commandValidator.validate(CREATE_ACTION + " " + CD_ACCOUNT_TYPE + " " + DEFAULT_ACCOUNT_ID_1
				+ " " + DEFAULT_APR_STRING + " " + MIN_CD_CREATE_BALANCE_STRING);
		assertTrue(actual);
	}

	@Test
	public void creating_cd_account_with_maximum_balance_is_valid() {
		boolean actual = commandValidator.validate(CREATE_ACTION + " " + CD_ACCOUNT_TYPE + " " + DEFAULT_ACCOUNT_ID_1
				+ " " + DEFAULT_APR_STRING + " " + MAX_CD_CREATE_BALANCE_STRING);
		assertTrue(actual);
	}

	@Test
	public void creating_cd_account_with_more_than_maximum_balance_is_invalid() {
		boolean actual = commandValidator.validate(CREATE_ACTION + " " + CD_ACCOUNT_TYPE + " " + DEFAULT_ACCOUNT_ID_1
				+ " " + DEFAULT_APR_STRING + " " + MAX_CD_CREATE_BALANCE_STRING + ".1");
		assertFalse(actual);
	}

	@Test
	public void creating_cd_account_with_no_balance_is_invalid() {
		boolean actual = commandValidator.validate(
				CREATE_ACTION + " " + CD_ACCOUNT_TYPE + " " + DEFAULT_ACCOUNT_ID_1 + " " + DEFAULT_APR_STRING);
		assertFalse(actual);
	}

}
