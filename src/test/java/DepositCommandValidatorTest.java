import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DepositCommandValidatorTest {
	private static final String DEPOSIT_ACTION = "Deposit";
	private static final String DEFAULT_ACCOUNT_ID = "12345678";
	private static final String DEFAULT_ACCOUNT_ID_2 = "98765432";
	private static final String DEFAULT_VALID_BALANCE_STRING = "500";
	private static final double DEFAULT_APR = 0.6;
	Bank bank;
	CommandValidator commandValidator;

	@BeforeEach
	public void setUp() {
		bank = new Bank();
		commandValidator = new DepositCommandValidator(bank);
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
				.validate(DEPOSIT_ACTION + " " + "12345abc" + " " + DEFAULT_VALID_BALANCE_STRING);
		assertFalse(actual);
	}

	@Test
	public void depositing_with_less_than_8_digit_id_is_invalid() {
		SavingsAccount savingsAccount = new SavingsAccount(DEFAULT_APR);
		bank.addAccount(DEFAULT_ACCOUNT_ID, savingsAccount);

		boolean actual = commandValidator
				.validate(DEPOSIT_ACTION + " " + "1234567" + " " + DEFAULT_VALID_BALANCE_STRING);
		assertFalse(actual);
	}

	@Test
	public void depositing_with_more_than_8_digit_id_is_invalid() {
		SavingsAccount savingsAccount = new SavingsAccount(DEFAULT_APR);
		bank.addAccount(DEFAULT_ACCOUNT_ID, savingsAccount);

		boolean actual = commandValidator
				.validate(DEPOSIT_ACTION + " " + "123456789" + " " + DEFAULT_VALID_BALANCE_STRING);
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

		boolean actual = commandValidator.validate(DEPOSIT_ACTION + " " + DEFAULT_ACCOUNT_ID + " " + "0");
		assertTrue(actual);
	}

	@Test
	public void depositing_into_savings_account_with_max_amount_is_valid() {
		SavingsAccount savingsAccount = new SavingsAccount(DEFAULT_APR);
		bank.addAccount(DEFAULT_ACCOUNT_ID, savingsAccount);

		boolean actual = commandValidator.validate(DEPOSIT_ACTION + " " + DEFAULT_ACCOUNT_ID + " " + "2500");
		assertTrue(actual);
	}

	@Test
	public void depositing_into_savings_account_with_more_than_max_amount_is_invalid() {
		SavingsAccount savingsAccount = new SavingsAccount(DEFAULT_APR);
		bank.addAccount(DEFAULT_ACCOUNT_ID, savingsAccount);

		boolean actual = commandValidator.validate(DEPOSIT_ACTION + " " + DEFAULT_ACCOUNT_ID + " " + "2500" + ".1");
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

		boolean actual = commandValidator.validate(DEPOSIT_ACTION + " " + DEFAULT_ACCOUNT_ID + " " + "0");
		assertTrue(actual);
	}

	@Test
	public void depositing_into_checking_account_with_max_amount_is_valid() {
		CheckingAccount checkingAccount = new CheckingAccount(DEFAULT_APR);
		bank.addAccount(DEFAULT_ACCOUNT_ID, checkingAccount);

		boolean actual = commandValidator.validate(DEPOSIT_ACTION + " " + DEFAULT_ACCOUNT_ID + " " + "1000");
		assertTrue(actual);
	}

	@Test
	public void depositing_into_checking_account_with_more_than_max_amount_is_invalid() {
		CheckingAccount checkingAccount = new CheckingAccount(DEFAULT_APR);
		bank.addAccount(DEFAULT_ACCOUNT_ID, checkingAccount);

		boolean actual = commandValidator.validate(DEPOSIT_ACTION + " " + DEFAULT_ACCOUNT_ID + " " + "1000" + ".1");
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
		CDAccount cdAccount = new CDAccount(DEFAULT_APR, 2000);
		bank.addAccount(DEFAULT_ACCOUNT_ID, cdAccount);

		boolean actual = commandValidator
				.validate(DEPOSIT_ACTION + " " + DEFAULT_ACCOUNT_ID + " " + DEFAULT_VALID_BALANCE_STRING);
		assertFalse(actual);
	}

	@Test
	public void depositing_into_cd_account_with_negative_amount_is_invalid() {
		CDAccount cdAccount = new CDAccount(DEFAULT_APR, 2000);
		bank.addAccount(DEFAULT_ACCOUNT_ID, cdAccount);

		boolean actual = commandValidator
				.validate(DEPOSIT_ACTION + " " + DEFAULT_ACCOUNT_ID + " " + "-" + DEFAULT_VALID_BALANCE_STRING);
		assertFalse(actual);
	}

	@Test
	public void depositing_into_cd_account_with_zero_amount_is_invalid() {
		CDAccount cdAccount = new CDAccount(DEFAULT_APR, 2000);
		bank.addAccount(DEFAULT_ACCOUNT_ID, cdAccount);

		boolean actual = commandValidator.validate(DEPOSIT_ACTION + " " + DEFAULT_ACCOUNT_ID + " " + "0");
		assertFalse(actual);
	}

	@Test
	public void depositing_into_cd_account_with_no_specified_amount_is_invalid() {
		CDAccount cdAccount = new CDAccount(DEFAULT_APR, 2000);
		bank.addAccount(DEFAULT_ACCOUNT_ID, cdAccount);

		boolean actual = commandValidator.validate(DEPOSIT_ACTION + " " + DEFAULT_ACCOUNT_ID);
		assertFalse(actual);
	}

}
