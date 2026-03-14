package banking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TransferHandlerTest {
	private static final String TRANSFER_ACTION = "Transfer";

	private static final String SOURCE_ID = "12345678";
	private static final String DEST_ID = "98765432";

	private static final double DEFAULT_APR = 0.6;

	private static final String ZERO_AMOUNT_STRING = "0";
	private static final double ZERO_BALANCE = 0;
	private static final double DEFAULT_BALANCE = 300;
	private static final String DEFAULT_TRANSFER_BALANCE_STRING = "200";
	private static final double DEFAULT_TRANSFER_BALANCE = 200;
	private static final String MORE_THAN_DEFAULT_BALANCE_STRING = "400";

	CommandHandler commandHandler;
	Bank bank;

	@BeforeEach
	public void setUp() {
		bank = new Bank();
		commandHandler = new CommandHandler(bank);
	}

	@Test
	public void transfer_from_checking_to_checking_with_valid_parameters() {
		CheckingAccount source = new CheckingAccount(DEFAULT_APR);
		CheckingAccount dest = new CheckingAccount(DEFAULT_APR);

		bank.addAccount(SOURCE_ID, source);
		bank.addAccount(DEST_ID, dest);

		bank.deposit(SOURCE_ID, DEFAULT_BALANCE);

		commandHandler
				.handle(TRANSFER_ACTION + " " + SOURCE_ID + " " + DEST_ID + " " + DEFAULT_TRANSFER_BALANCE_STRING);

		assertEquals(DEFAULT_BALANCE - DEFAULT_TRANSFER_BALANCE, bank.getAccounts().get(SOURCE_ID).getBalance());
		assertEquals(DEFAULT_TRANSFER_BALANCE, bank.getAccounts().get(DEST_ID).getBalance());
	}

	@Test
	public void transfer_from_checking_to_savings_with_valid_parameters() {
		CheckingAccount source = new CheckingAccount(DEFAULT_APR);
		SavingsAccount dest = new SavingsAccount(DEFAULT_APR);

		bank.addAccount(SOURCE_ID, source);
		bank.addAccount(DEST_ID, dest);

		bank.deposit(SOURCE_ID, DEFAULT_BALANCE);

		commandHandler
				.handle(TRANSFER_ACTION + " " + SOURCE_ID + " " + DEST_ID + " " + DEFAULT_TRANSFER_BALANCE_STRING);

		assertEquals(DEFAULT_BALANCE - DEFAULT_TRANSFER_BALANCE, bank.getAccounts().get(SOURCE_ID).getBalance());
		assertEquals(DEFAULT_TRANSFER_BALANCE, bank.getAccounts().get(DEST_ID).getBalance());
	}

	@Test
	public void transfer_from_savings_to_checking_with_valid_parameters() {
		SavingsAccount source = new SavingsAccount(DEFAULT_APR);
		CheckingAccount dest = new CheckingAccount(DEFAULT_APR);

		bank.addAccount(SOURCE_ID, source);
		bank.addAccount(DEST_ID, dest);

		bank.deposit(SOURCE_ID, DEFAULT_BALANCE);

		commandHandler
				.handle(TRANSFER_ACTION + " " + SOURCE_ID + " " + DEST_ID + " " + DEFAULT_TRANSFER_BALANCE_STRING);

		assertEquals(DEFAULT_BALANCE - DEFAULT_TRANSFER_BALANCE, bank.getAccounts().get(SOURCE_ID).getBalance());
		assertEquals(DEFAULT_TRANSFER_BALANCE, bank.getAccounts().get(DEST_ID).getBalance());
	}

	@Test
	public void transfer_from_savings_to_savings_with_valid_parameters() {
		SavingsAccount source = new SavingsAccount(DEFAULT_APR);
		SavingsAccount dest = new SavingsAccount(DEFAULT_APR);

		bank.addAccount(SOURCE_ID, source);
		bank.addAccount(DEST_ID, dest);

		bank.deposit(SOURCE_ID, DEFAULT_BALANCE);

		commandHandler
				.handle(TRANSFER_ACTION + " " + SOURCE_ID + " " + DEST_ID + " " + DEFAULT_TRANSFER_BALANCE_STRING);

		assertEquals(DEFAULT_BALANCE - DEFAULT_TRANSFER_BALANCE, bank.getAccounts().get(SOURCE_ID).getBalance());
		assertEquals(DEFAULT_TRANSFER_BALANCE, bank.getAccounts().get(DEST_ID).getBalance());
	}

	@Test
	public void transfer_more_than_source_balance_transfers_remaining_balance() {
		CheckingAccount source = new CheckingAccount(DEFAULT_APR);
		CheckingAccount dest = new CheckingAccount(DEFAULT_APR);

		bank.addAccount(SOURCE_ID, source);
		bank.addAccount(DEST_ID, dest);

		bank.deposit(SOURCE_ID, DEFAULT_BALANCE);

		commandHandler
				.handle(TRANSFER_ACTION + " " + SOURCE_ID + " " + DEST_ID + " " + MORE_THAN_DEFAULT_BALANCE_STRING);

		assertEquals(ZERO_BALANCE, bank.getAccounts().get(SOURCE_ID).getBalance());
		assertEquals(DEFAULT_BALANCE, bank.getAccounts().get(DEST_ID).getBalance());
	}

	@Test
	public void transfer_from_empty_account_transfers_zero() {
		CheckingAccount source = new CheckingAccount(DEFAULT_APR);
		CheckingAccount dest = new CheckingAccount(DEFAULT_APR);

		bank.addAccount(SOURCE_ID, source);
		bank.addAccount(DEST_ID, dest);

		commandHandler
				.handle(TRANSFER_ACTION + " " + SOURCE_ID + " " + DEST_ID + " " + DEFAULT_TRANSFER_BALANCE_STRING);

		assertEquals(ZERO_BALANCE, bank.getAccounts().get(SOURCE_ID).getBalance());
		assertEquals(ZERO_BALANCE, bank.getAccounts().get(DEST_ID).getBalance());
	}

	@Test
	public void transfer_zero_amount_does_not_change_balances() {
		CheckingAccount source = new CheckingAccount(DEFAULT_APR);
		CheckingAccount dest = new CheckingAccount(DEFAULT_APR);

		bank.addAccount(SOURCE_ID, source);
		bank.addAccount(DEST_ID, dest);

		bank.deposit(SOURCE_ID, DEFAULT_TRANSFER_BALANCE);

		commandHandler.handle(TRANSFER_ACTION + " " + SOURCE_ID + " " + DEST_ID + " " + ZERO_AMOUNT_STRING);

		assertEquals(DEFAULT_TRANSFER_BALANCE, bank.getAccounts().get(SOURCE_ID).getBalance());
		assertEquals(ZERO_BALANCE, bank.getAccounts().get(DEST_ID).getBalance());
	}
}
