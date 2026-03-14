package banking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PassTimeValidatorTest {
	private static final String PASS_TIME_ACTION = "Pass";
	private static final String PASS_MIN_MONTH_STRING = "1";
	private static final String PASS_3_MONTHS_STRING = "3";
	private static final String PASS_MAX_MONTH_STRING = "60";
	private static final String PASS_0_MONTH_STRING = "0";
	private static final String PASS_61_MONTHS_STRING = "61";
	Bank bank;
	CommandValidator commandValidator;

	@BeforeEach
	public void setUp() {
		bank = new Bank();
		commandValidator = new CommandValidator(bank);
	}

	@Test
	public void pass_valid_number_of_months_is_valid() {
		boolean actual = commandValidator.validate(PASS_TIME_ACTION + " " + PASS_3_MONTHS_STRING);
		assertTrue(actual);
	}

	@Test
	public void pass_min_number_of_months_is_valid() {
		boolean actual = commandValidator.validate(PASS_TIME_ACTION + " " + PASS_MIN_MONTH_STRING);
		assertTrue(actual);
	}

	@Test
	public void pass_max_number_of_months_is_valid() {
		boolean actual = commandValidator.validate(PASS_TIME_ACTION + " " + PASS_MAX_MONTH_STRING);
		assertTrue(actual);
	}

	@Test
	public void pass_zero_number_of_months_is_invalid() {
		boolean actual = commandValidator.validate(PASS_TIME_ACTION + " " + PASS_0_MONTH_STRING);
		assertFalse(actual);
	}

	@Test
	public void pass_more_than_max_number_of_months_is_invalid() {
		boolean actual = commandValidator.validate(PASS_TIME_ACTION + " " + PASS_61_MONTHS_STRING);
		assertFalse(actual);
	}

	@Test
	public void pass_negative_number_of_months_is_invalid() {
		boolean actual = commandValidator.validate(PASS_TIME_ACTION + " " + "-" + " " + PASS_3_MONTHS_STRING);
		assertFalse(actual);
	}

	@Test
	public void pass_without_specified_number_of_months_is_invalid() {
		boolean actual = commandValidator.validate(PASS_TIME_ACTION);
		assertFalse(actual);
	}

	@Test
	public void pass_with_more_than_needed_parameters_is_invalid() {
		boolean actual = commandValidator
				.validate(PASS_TIME_ACTION + " " + PASS_3_MONTHS_STRING + " " + PASS_3_MONTHS_STRING);
		assertFalse(actual);
	}

}
