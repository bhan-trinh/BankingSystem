package banking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TransferValidatorTest {
	private static final String WITHDRAW_ACTION = "Withdraw";
	private static final String WITHDRAW_ACTION_CASE_INSENSITIVE = "WIthDrAw";
	private static final String SOURCE_ID = "12345678";
	private static final String DEST_ID = "98765432";
	private static final String NON_NUMERIC_ID = "12345abc";
	private static final String SEVEN_DIGIT_ID = "1234567";
	private static final String NINE_DIGIT_ID = "123456789";
	private static final double DEFAULT_APR = 0.6;
	private static final String ZERO_AMOUNT_STRING = "0";
	private static final int ZERO_BALANCE = 0;
	private static final String TRANSFER_ACTION = "Transfer";

	Bank bank;
	CommandValidator commandValidator;

	@BeforeEach
	public void setUp() {
		bank = new Bank();
		commandValidator = new CommandValidator(bank);
	}

	// ===== VALID =====
	@Test
	public void transfer_from_checking_to_checking_with_valid_parameters() {
		CheckingAccount source = new CheckingAccount(DEFAULT_APR);
		CheckingAccount dest = new CheckingAccount(DEFAULT_APR);

		bank.addAccount(SOURCE_ID, source);
		bank.addAccount(DEST_ID, dest);

		bank.deposit(SOURCE_ID, 300);

		boolean actual = commandValidator.validate(TRANSFER_ACTION + " " + SOURCE_ID + " " + DEST_ID + " 200");
		assertTrue(actual);
	}

	@Test
	public void transfer_from_checking_to_savings_with_valid_parameters() {
		CheckingAccount source = new CheckingAccount(DEFAULT_APR);
		SavingsAccount dest = new SavingsAccount(DEFAULT_APR);

		bank.addAccount(SOURCE_ID, source);
		bank.addAccount(DEST_ID, dest);

		bank.deposit(SOURCE_ID, 300);

		boolean actual = commandValidator.validate(TRANSFER_ACTION + " " + SOURCE_ID + " " + DEST_ID + " 200");
		assertTrue(actual);
	}

	@Test
	public void transfer_from_savings_to_checking_with_valid_parameters() {
		SavingsAccount source = new SavingsAccount(DEFAULT_APR);
		CheckingAccount dest = new CheckingAccount(DEFAULT_APR);

		bank.addAccount(SOURCE_ID, source);
		bank.addAccount(DEST_ID, dest);

		bank.deposit(SOURCE_ID, 300);

		boolean actual = commandValidator.validate(TRANSFER_ACTION + " " + SOURCE_ID + " " + DEST_ID + " 200");
		assertTrue(actual);
	}

	@Test
	public void transfer_from_savings_to_savings_with_valid_parameters() {
		SavingsAccount source = new SavingsAccount(DEFAULT_APR);
		SavingsAccount dest = new SavingsAccount(DEFAULT_APR);

		bank.addAccount(SOURCE_ID, source);
		bank.addAccount(DEST_ID, dest);

		bank.deposit(SOURCE_ID, 300);

		boolean actual = commandValidator.validate(TRANSFER_ACTION + " " + SOURCE_ID + " " + DEST_ID + " 200");
		assertTrue(actual);
	}

	@Test
	public void transfer_more_than_source_balance_transfers_remaining_balance() {
		CheckingAccount source = new CheckingAccount(DEFAULT_APR);
		CheckingAccount dest = new CheckingAccount(DEFAULT_APR);

		bank.addAccount(SOURCE_ID, source);
		bank.addAccount(DEST_ID, dest);

		bank.deposit(SOURCE_ID, 200);

		boolean actual = commandValidator.validate(TRANSFER_ACTION + " " + SOURCE_ID + " " + DEST_ID + " 300");
		assertTrue(actual);
	}

	@Test
	public void transfer_from_empty_account_transfers_zero() {
		Account source = new CheckingAccount(DEFAULT_APR);
		Account dest = new CheckingAccount(DEFAULT_APR);

		bank.addAccount(SOURCE_ID, source);
		bank.addAccount(DEST_ID, dest);

		boolean actual = commandValidator.validate(TRANSFER_ACTION + " " + SOURCE_ID + " " + DEST_ID + " 200");
		assertTrue(actual);
	}

	@Test
	public void transfer_zero_amount_does_not_change_balances() {
		Account source = new CheckingAccount(DEFAULT_APR);
		Account dest = new CheckingAccount(DEFAULT_APR);

		bank.addAccount(SOURCE_ID, source);
		bank.addAccount(DEST_ID, dest);

		bank.deposit(SOURCE_ID, 200);

		boolean actual = commandValidator.validate(TRANSFER_ACTION + " " + SOURCE_ID + " " + DEST_ID + " 0");
		assertTrue(actual);
	}

	// ===== AMOUNT ERRORS =====
	@Test
	public void transfer_with_negative_amount_is_invalid() {
		Account source = new CheckingAccount(DEFAULT_APR);
		Account dest = new CheckingAccount(DEFAULT_APR);

		bank.addAccount(SOURCE_ID, source);
		bank.addAccount(DEST_ID, dest);

		boolean actual = commandValidator.validate(TRANSFER_ACTION + " " + SOURCE_ID + " " + DEST_ID + " -100");

		assertFalse(actual);
	}

	@Test
	public void transfer_without_amount_is_invalid() {
		Account source = new CheckingAccount(DEFAULT_APR);
		Account dest = new CheckingAccount(DEFAULT_APR);

		bank.addAccount(SOURCE_ID, source);
		bank.addAccount(DEST_ID, dest);

		boolean actual = commandValidator.validate(TRANSFER_ACTION + " " + SOURCE_ID + " " + DEST_ID);

		assertFalse(actual);
	}

	@Test
	public void transfer_with_too_many_arguments_is_invalid() {
		Account source = new CheckingAccount(DEFAULT_APR);
		Account dest = new CheckingAccount(DEFAULT_APR);

		bank.addAccount(SOURCE_ID, source);
		bank.addAccount(DEST_ID, dest);

		boolean actual = commandValidator.validate(TRANSFER_ACTION + " " + SOURCE_ID + " " + DEST_ID + " 100 200");

		assertFalse(actual);
	}

	// ===== ID ERRORS =====
	@Test
	public void transfer_with_same_source_and_destination_is_invalid() {
		Account source = new CheckingAccount(DEFAULT_APR);
		Account dest = new CheckingAccount(DEFAULT_APR);

		bank.addAccount(SOURCE_ID, source);
		bank.addAccount(DEST_ID, dest);

		boolean actual = commandValidator.validate(TRANSFER_ACTION + " " + SOURCE_ID + " " + SOURCE_ID + " 100");

		assertFalse(actual);
	}

	@Test
	public void transfer_with_non_existing_source_account_is_invalid() {
		Account source = new CheckingAccount(DEFAULT_APR);
		Account dest = new CheckingAccount(DEFAULT_APR);

		bank.addAccount(SOURCE_ID, source);
		bank.addAccount(DEST_ID, dest);

		boolean actual = commandValidator.validate(TRANSFER_ACTION + " " + "99999999" + " " + DEST_ID + " 100");

		assertFalse(actual);
	}

	@Test
	public void transfer_with_non_existing_dest_account_is_invalid() {
		Account source = new CheckingAccount(DEFAULT_APR);
		Account dest = new CheckingAccount(DEFAULT_APR);

		bank.addAccount(SOURCE_ID, source);
		bank.addAccount(DEST_ID, dest);

		boolean actual = commandValidator.validate(TRANSFER_ACTION + " " + SOURCE_ID + "99999999" + " " + "100");

		assertFalse(actual);
	}

	@Test
	public void transferring_from_checking_to_checking_with_non_numeric_source_id_is_invalid() {
		Account source = new CheckingAccount(DEFAULT_APR);
		Account dest = new CheckingAccount(DEFAULT_APR);

		bank.addAccount(SOURCE_ID, source);
		bank.addAccount(DEST_ID, dest);

		boolean actual = commandValidator
				.validate(TRANSFER_ACTION + " " + NON_NUMERIC_ID + " " + DEST_ID + " " + "100");

		assertFalse(actual);
	}

	@Test
	public void transferring_from_checking_to_checking_with_non_numeric_destination_id_is_invalid() {
		Account source = new CheckingAccount(DEFAULT_APR);
		Account dest = new CheckingAccount(DEFAULT_APR);

		bank.addAccount(SOURCE_ID, source);
		bank.addAccount(DEST_ID, dest);

		boolean actual = commandValidator
				.validate(TRANSFER_ACTION + " " + SOURCE_ID + " " + NON_NUMERIC_ID + " " + "100");

		assertFalse(actual);
	}

	@Test
	public void transferring_from_checking_to_checking_with_less_than_8_digit_source_id_is_invalid() {
		Account source = new CheckingAccount(DEFAULT_APR);
		Account dest = new CheckingAccount(DEFAULT_APR);

		bank.addAccount(SOURCE_ID, source);
		bank.addAccount(DEST_ID, dest);

		boolean actual = commandValidator
				.validate(TRANSFER_ACTION + " " + SEVEN_DIGIT_ID + " " + DEST_ID + " " + "100");

		assertFalse(actual);
	}

	@Test
	public void transferring_from_checking_to_checking_with_less_than_8_digit_destination_id_is_invalid() {
		Account source = new CheckingAccount(DEFAULT_APR);
		Account dest = new CheckingAccount(DEFAULT_APR);

		bank.addAccount(SOURCE_ID, source);
		bank.addAccount(DEST_ID, dest);

		boolean actual = commandValidator
				.validate(TRANSFER_ACTION + " " + SOURCE_ID + " " + SEVEN_DIGIT_ID + " " + "100");

		assertFalse(actual);
	}

	@Test
	public void transferring_from_checking_to_checking_with_more_than_8_digit_source_id_is_invalid() {
		Account source = new CheckingAccount(DEFAULT_APR);
		Account dest = new CheckingAccount(DEFAULT_APR);

		bank.addAccount(SOURCE_ID, source);
		bank.addAccount(DEST_ID, dest);

		boolean actual = commandValidator.validate(TRANSFER_ACTION + " " + NINE_DIGIT_ID + " " + DEST_ID + " " + "100");

		assertFalse(actual);
	}

	@Test
	public void transferring_from_checking_to_checking_with_more_than_8_digit_destination_id_is_invalid() {
		Account source = new CheckingAccount(DEFAULT_APR);
		Account dest = new CheckingAccount(DEFAULT_APR);

		bank.addAccount(SOURCE_ID, source);
		bank.addAccount(DEST_ID, dest);

		boolean actual = commandValidator
				.validate(TRANSFER_ACTION + " " + SOURCE_ID + " " + NINE_DIGIT_ID + " " + "100");

		assertFalse(actual);
	}

	// ===== CD =====
	@Test
	public void transfer_from_cd_account_is_invalid() {
		CDAccount cdAccount = new CDAccount(DEFAULT_APR, 100);
		bank.addAccount(SOURCE_ID, cdAccount);
		bank.pass(12);
		String cdBalanceString = (cdAccount.getBalance()) + "";

		CheckingAccount checking = new CheckingAccount(DEFAULT_APR);
		bank.addAccount(DEST_ID, checking);

		boolean actual = commandValidator
				.validate(TRANSFER_ACTION + " " + SOURCE_ID + " " + DEST_ID + " " + cdBalanceString);

		assertFalse(actual);
	}

	@Test
	public void transfer_to_cd_account_is_invalid() {
		CDAccount cdAccount = new CDAccount(DEFAULT_APR, 1000);
		bank.addAccount(DEST_ID, cdAccount);
		bank.pass(12);

		CheckingAccount checking = new CheckingAccount(DEFAULT_APR);
		bank.addAccount(SOURCE_ID, checking);

		boolean actual = commandValidator.validate(TRANSFER_ACTION + " " + SOURCE_ID + " " + DEST_ID + " 100");

		assertFalse(actual);
	}

}
