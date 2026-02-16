import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
	public void savings_account_can_be_created_with_valid_parameter() {
		boolean actual = commandValidator.validate("Create savings 12345678 0.6");
		assertTrue(actual);
	}

}
