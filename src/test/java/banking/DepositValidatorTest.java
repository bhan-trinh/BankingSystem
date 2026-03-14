package banking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DepositValidatorTest {
	private static final String DEPOSIT_ACTION = "Deposit";
	private static final String DEPOSIT_ACTION_CASE_INSENSITIVE = "dePosIt";
	private static final String DEFAULT_ACCOUNT_ID = "12345678";
	private static final String DEFAULT_ACCOUNT_ID_2 = "98765432";
	private static final String NON_NUMERIC_ID = "12345abc";
	private static final String SEVEN_DIGIT_ID = "1234567";
	private static final String NINE_DIGIT_ID = "123456789";
	private static final double DEFAULT_APR = 0.6;
	private static final String DEFAULT_VALID_BALANCE_STRING = "500";
	private static final String SAVINGS_MAX_DEPOSIT_AMOUNT_STRING = "2500";
	private static final String CHECKING_MAX_DEPOSIT_AMOUNT_STRING = "1000";
	private static final String ZERO_AMOUNT_STRING = "0";
	private static final int DEFAULT_CD_BALANCE = 2000;
	Bank bank;
	CommandValidator commandValidator;

	@BeforeEach
	public void setUp() {
		bank = new Bank();
		commandValidator = new CommandValidator(bank);
	}

	// ===== GENERAL =====
	@Test
	public void depositing_with_no_specified_id_is_invalid() {
		SavingsAccount savingsAccount = new SavingsAccount(DEFAULT_APR);
		bank.addAccount(DEFAULT_ACCOUNT_ID, savingsAccount);

		boolean actual = commandValidator.validate(DEPOSIT_ACTION + " " + DEFAULT_VALID_BALANCE_STRING);
		assertFalse(actual);
	}

	@Test
	public void depositing_with_no_parameters_is_invalid() {
		SavingsAccount savingsAccount = new SavingsAccount(DEFAULT_APR);
		bank.addAccount(DEFAULT_ACCOUNT_ID, savingsAccount);

		boolean actual = commandValidator.validate(DEPOSIT_ACTION);
		assertFalse(actual);
	}

	@Test
	public void depositing_with_more_than_needed_parameters_is_invalid() {
		SavingsAccount savingsAccount = new SavingsAccount(DEFAULT_APR);
		bank.addAccount(DEFAULT_ACCOUNT_ID, savingsAccount);

		boolean actual = commandValidator.validate(DEPOSIT_ACTION + " " + DEFAULT_ACCOUNT_ID + " "
				+ DEFAULT_VALID_BALANCE_STRING + " " + DEFAULT_VALID_BALANCE_STRING);
		assertFalse(actual);
	}

	@Test
	public void depositing_with_non_existing_id_is_invalid() {
		SavingsAccount savingsAccount = new SavingsAccount(DEFAULT_APR);
		bank.addAccount(DEFAULT_ACCOUNT_ID, savingsAccount);

		boolean actual = commandValidator
				.validate(DEPOSIT_ACTION + " " + DEFAULT_ACCOUNT_ID_2 + " " + DEFAULT_VALID_BALANCE_STRING);
		assertFalse(actual);
	}

	@Test
	public void depositing_with_non_numeric_id_is_invalid() {
		SavingsAccount savingsAccount = new SavingsAccount(DEFAULT_APR);
		bank.addAccount(DEFAULT_ACCOUNT_ID, savingsAccount);

		boolean actual = commandValidator
				.validate(DEPOSIT_ACTION + " " + NON_NUMERIC_ID + " " + DEFAULT_VALID_BALANCE_STRING);
		assertFalse(actual);
	}

	@Test
	public void depositing_with_less_than_8_digit_id_is_invalid() {
		SavingsAccount savingsAccount = new SavingsAccount(DEFAULT_APR);
		bank.addAccount(DEFAULT_ACCOUNT_ID, savingsAccount);

		boolean actual = commandValidator
				.validate(DEPOSIT_ACTION + " " + SEVEN_DIGIT_ID + " " + DEFAULT_VALID_BALANCE_STRING);
		assertFalse(actual);
	}

	@Test
	public void depositing_with_more_than_8_digit_id_is_invalid() {
		SavingsAccount savingsAccount = new SavingsAccount(DEFAULT_APR);
		bank.addAccount(DEFAULT_ACCOUNT_ID, savingsAccount);

		boolean actual = commandValidator
				.validate(DEPOSIT_ACTION + " " + NINE_DIGIT_ID + " " + DEFAULT_VALID_BALANCE_STRING);
		assertFalse(actual);
	}

	@Test
	public void depositing_with_wrong_parameter_order_is_invalid() {
		SavingsAccount savingsAccount = new SavingsAccount(DEFAULT_APR);
		bank.addAccount(DEFAULT_ACCOUNT_ID, savingsAccount);

		boolean actual = commandValidator
				.validate(DEPOSIT_ACTION + " " + DEFAULT_VALID_BALANCE_STRING + " " + NINE_DIGIT_ID);
		assertFalse(actual);
	}

	@Test
	public void depositing_with_wrong_parameter_order_is_invalid_2() {
		SavingsAccount savingsAccount = new SavingsAccount(DEFAULT_APR);
		bank.addAccount(DEFAULT_ACCOUNT_ID, savingsAccount);

		boolean actual = commandValidator
				.validate(DEFAULT_VALID_BALANCE_STRING + " " + NINE_DIGIT_ID + " " + DEPOSIT_ACTION);
		assertFalse(actual);
	}

	// ===== SAVINGS =====
	@Test
	public void depositing_into_savings_account_with_valid_parameters_is_valid() {
		SavingsAccount savingsAccount = new SavingsAccount(DEFAULT_APR);
		bank.addAccount(DEFAULT_ACCOUNT_ID, savingsAccount);

		boolean actual = commandValidator
				.validate(DEPOSIT_ACTION + " " + DEFAULT_ACCOUNT_ID + " " + DEFAULT_VALID_BALANCE_STRING);
		assertTrue(actual);
	}

	@Test
	public void depositing_into_savings_account_with_case_insensitive_is_valid() {
		SavingsAccount savingsAccount = new SavingsAccount(DEFAULT_APR);
		bank.addAccount(DEFAULT_ACCOUNT_ID, savingsAccount);

		boolean actual = commandValidator.validate(
				DEPOSIT_ACTION_CASE_INSENSITIVE + " " + DEFAULT_ACCOUNT_ID + " " + DEFAULT_VALID_BALANCE_STRING);
		assertTrue(actual);
	}

	@Test
	public void depositing_into_savings_account_with_negative_amount_is_invalid() {
		SavingsAccount savingsAccount = new SavingsAccount(DEFAULT_APR);
		bank.addAccount(DEFAULT_ACCOUNT_ID, savingsAccount);

		boolean actual = commandValidator
				.validate(DEPOSIT_ACTION + " " + DEFAULT_ACCOUNT_ID + " " + "-" + DEFAULT_VALID_BALANCE_STRING);
		assertFalse(actual);
	}

	@Test
	public void depositing_into_savings_account_with_zero_amount_is_valid() {
		SavingsAccount savingsAccount = new SavingsAccount(DEFAULT_APR);
		bank.addAccount(DEFAULT_ACCOUNT_ID, savingsAccount);

		boolean actual = commandValidator
				.validate(DEPOSIT_ACTION + " " + DEFAULT_ACCOUNT_ID + " " + ZERO_AMOUNT_STRING);
		assertTrue(actual);
	}

	@Test
	public void depositing_into_savings_account_with_max_amount_is_valid() {
		SavingsAccount savingsAccount = new SavingsAccount(DEFAULT_APR);
		bank.addAccount(DEFAULT_ACCOUNT_ID, savingsAccount);

		boolean actual = commandValidator
				.validate(DEPOSIT_ACTION + " " + DEFAULT_ACCOUNT_ID + " " + SAVINGS_MAX_DEPOSIT_AMOUNT_STRING);
		assertTrue(actual);
	}

	@Test
	public void depositing_into_savings_account_with_more_than_max_amount_is_invalid() {
		SavingsAccount savingsAccount = new SavingsAccount(DEFAULT_APR);
		bank.addAccount(DEFAULT_ACCOUNT_ID, savingsAccount);

		boolean actual = commandValidator
				.validate(DEPOSIT_ACTION + " " + DEFAULT_ACCOUNT_ID + " " + SAVINGS_MAX_DEPOSIT_AMOUNT_STRING + ".1");
		assertFalse(actual);
	}

	@Test
	public void depositing_into_savings_account_with_no_specified_amount_is_invalid() {
		SavingsAccount savingsAccount = new SavingsAccount(DEFAULT_APR);
		bank.addAccount(DEFAULT_ACCOUNT_ID, savingsAccount);

		boolean actual = commandValidator.validate(DEPOSIT_ACTION + " " + DEFAULT_ACCOUNT_ID);
		assertFalse(actual);
	}

	// ===== CHECKING =====
	@Test
	public void depositing_into_checking_account_with_valid_parameters_is_valid() {
		CheckingAccount checkingAccount = new CheckingAccount(DEFAULT_APR);
		bank.addAccount(DEFAULT_ACCOUNT_ID, checkingAccount);

		boolean actual = commandValidator
				.validate(DEPOSIT_ACTION + " " + DEFAULT_ACCOUNT_ID + " " + DEFAULT_VALID_BALANCE_STRING);
		assertTrue(actual);
	}

	@Test
	public void depositing_into_checking_account_with_negative_amount_is_invalid() {
		CheckingAccount checkingAccount = new CheckingAccount(DEFAULT_APR);
		bank.addAccount(DEFAULT_ACCOUNT_ID, checkingAccount);

		boolean actual = commandValidator
				.validate(DEPOSIT_ACTION + " " + DEFAULT_ACCOUNT_ID + " " + "-" + DEFAULT_VALID_BALANCE_STRING);
		assertFalse(actual);
	}

	@Test
	public void depositing_into_checking_account_with_zero_amount_is_valid() {
		CheckingAccount checkingAccount = new CheckingAccount(DEFAULT_APR);
		bank.addAccount(DEFAULT_ACCOUNT_ID, checkingAccount);

		boolean actual = commandValidator
				.validate(DEPOSIT_ACTION + " " + DEFAULT_ACCOUNT_ID + " " + ZERO_AMOUNT_STRING);
		assertTrue(actual);
	}

	@Test
	public void depositing_into_checking_account_with_max_amount_is_valid() {
		CheckingAccount checkingAccount = new CheckingAccount(DEFAULT_APR);
		bank.addAccount(DEFAULT_ACCOUNT_ID, checkingAccount);

		boolean actual = commandValidator
				.validate(DEPOSIT_ACTION + " " + DEFAULT_ACCOUNT_ID + " " + CHECKING_MAX_DEPOSIT_AMOUNT_STRING);
		assertTrue(actual);
	}

	@Test
	public void depositing_into_checking_account_with_more_than_max_amount_is_invalid() {
		CheckingAccount checkingAccount = new CheckingAccount(DEFAULT_APR);
		bank.addAccount(DEFAULT_ACCOUNT_ID, checkingAccount);

		boolean actual = commandValidator
				.validate(DEPOSIT_ACTION + " " + DEFAULT_ACCOUNT_ID + " " + CHECKING_MAX_DEPOSIT_AMOUNT_STRING + ".1");
		assertFalse(actual);
	}

	@Test
	public void depositing_into_checking_account_with_no_specified_amount_is_invalid() {
		CheckingAccount checkingAccount = new CheckingAccount(DEFAULT_APR);
		bank.addAccount(DEFAULT_ACCOUNT_ID, checkingAccount);

		boolean actual = commandValidator.validate(DEPOSIT_ACTION + " " + DEFAULT_ACCOUNT_ID);
		assertFalse(actual);
	}

	// ===== CD =====
	@Test
	public void depositing_into_cd_account_is_invalid() {
		CDAccount cdAccount = new CDAccount(DEFAULT_APR, DEFAULT_CD_BALANCE);
		bank.addAccount(DEFAULT_ACCOUNT_ID, cdAccount);

		boolean actual = commandValidator
				.validate(DEPOSIT_ACTION + " " + DEFAULT_ACCOUNT_ID + " " + DEFAULT_VALID_BALANCE_STRING);
		assertFalse(actual);
	}

	@Test
	public void depositing_into_cd_account_with_negative_amount_is_invalid() {
		CDAccount cdAccount = new CDAccount(DEFAULT_APR, DEFAULT_CD_BALANCE);
		bank.addAccount(DEFAULT_ACCOUNT_ID, cdAccount);

		boolean actual = commandValidator
				.validate(DEPOSIT_ACTION + " " + DEFAULT_ACCOUNT_ID + " " + "-" + DEFAULT_VALID_BALANCE_STRING);
		assertFalse(actual);
	}

	@Test
	public void depositing_into_cd_account_with_zero_amount_is_invalid() {
		CDAccount cdAccount = new CDAccount(DEFAULT_APR, DEFAULT_CD_BALANCE);
		bank.addAccount(DEFAULT_ACCOUNT_ID, cdAccount);

		boolean actual = commandValidator
				.validate(DEPOSIT_ACTION + " " + DEFAULT_ACCOUNT_ID + " " + ZERO_AMOUNT_STRING);
		assertFalse(actual);
	}

	@Test
	public void depositing_into_cd_account_with_no_specified_amount_is_invalid() {
		CDAccount cdAccount = new CDAccount(DEFAULT_APR, DEFAULT_CD_BALANCE);
		bank.addAccount(DEFAULT_ACCOUNT_ID, cdAccount);

		boolean actual = commandValidator.validate(DEPOSIT_ACTION + " " + DEFAULT_ACCOUNT_ID);
		assertFalse(actual);
	}

}
