package banking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class WithdrawValidatorTest {
	private static final String WITHDRAW_ACTION = "Withdraw";
	private static final String DEFAULT_ACCOUNT_ID = "12345678";
	private static final String DEFAULT_ACCOUNT_ID_2 = "98765432";
	private static final String NON_NUMERIC_ID = "12345abc";
	private static final String SEVEN_DIGIT_ID = "1234567";
	private static final String NINE_DIGIT_ID = "123456789";
	private static final double DEFAULT_APR = 0.6;
	private static final String DEFAULT_VALID_BALANCE_STRING = "300";
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

	@Test
	public void depositing_into_savings_account_with_valid_parameters_is_valid() {
		SavingsAccount savingsAccount = new SavingsAccount(DEFAULT_APR);
		bank.addAccount(DEFAULT_ACCOUNT_ID, savingsAccount);

		boolean actual = commandValidator
				.validate(WITHDRAW_ACTION + " " + DEFAULT_ACCOUNT_ID + " " + DEFAULT_VALID_BALANCE_STRING);
		assertTrue(actual);
	}
}
