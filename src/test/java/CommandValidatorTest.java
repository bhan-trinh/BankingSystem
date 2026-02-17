import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CommandValidatorTest {
	Bank bank;
	CommandValidator commandValidator;

	@BeforeEach
	public void setUp() {
		bank = new Bank();
		commandValidator = new CommandValidator(bank);
	}

	@Test
	public void savings_account_can_be_created_with_valid_parameters() {
		boolean actual = commandValidator.validate("Create savings 12345678 0.6");
		assertTrue(actual);
	}

	@Test
	public void savings_account_can_be_created_with_valid_parameters_case_insensitive() {
		boolean actual = commandValidator.validate("CrEaTe sAvInGs 12345678 0.6");
		assertTrue(actual);
	}

	@Test
	public void savings_account_can_be_created_with_valid_parameters_in_bank_with_other_accounts() {
		CheckingAccount checkingAccount = new CheckingAccount(0.3);
		bank.addAccount("98765432", checkingAccount);
		boolean actual = commandValidator.validate("Create savings 12345678 0.6");
		assertTrue(actual);
	}

	@Test
	public void creating_savings_account_with_duplicate_id_is_invalid() {
		CheckingAccount checkingAccount = new CheckingAccount(0.3);
		bank.addAccount("12345678", checkingAccount);
		boolean actual = commandValidator.validate("Create savings 12345678 0.6");
		assertFalse(actual);
	}

}
