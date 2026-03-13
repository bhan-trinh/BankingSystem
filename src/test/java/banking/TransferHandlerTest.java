package banking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TransferHandlerTest {
	private static final String SOURCE_ID = "12345678";
	private static final String DEST_ID = "98765432";
	private static final double DEFAULT_APR = 0.6;
	private static final String ZERO_AMOUNT_STRING = "0";
	private static final int ZERO_BALANCE = 0;
	private static final String TRANSFER_ACTION = "Transfer";

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

		bank.deposit(SOURCE_ID, 300);

		commandHandler.handle(TRANSFER_ACTION + " " + SOURCE_ID + " " + DEST_ID + " 200");

		assertEquals(100, bank.getAccounts().get(SOURCE_ID).getBalance());
		assertEquals(200, bank.getAccounts().get(DEST_ID).getBalance());
	}

	@Test
	public void transfer_from_checking_to_savings_with_valid_parameters() {
		CheckingAccount source = new CheckingAccount(DEFAULT_APR);
		SavingsAccount dest = new SavingsAccount(DEFAULT_APR);

		bank.addAccount(SOURCE_ID, source);
		bank.addAccount(DEST_ID, dest);

		bank.deposit(SOURCE_ID, 300);

		commandHandler.handle(TRANSFER_ACTION + " " + SOURCE_ID + " " + DEST_ID + " 200");

		assertEquals(100, bank.getAccounts().get(SOURCE_ID).getBalance());
		assertEquals(200, bank.getAccounts().get(DEST_ID).getBalance());
	}

	@Test
	public void transfer_from_savings_to_checking_with_valid_parameters() {
		SavingsAccount source = new SavingsAccount(DEFAULT_APR);
		CheckingAccount dest = new CheckingAccount(DEFAULT_APR);

		bank.addAccount(SOURCE_ID, source);
		bank.addAccount(DEST_ID, dest);

		bank.deposit(SOURCE_ID, 300);

		commandHandler.handle(TRANSFER_ACTION + " " + SOURCE_ID + " " + DEST_ID + " 200");

		assertEquals(100, bank.getAccounts().get(SOURCE_ID).getBalance());
		assertEquals(200, bank.getAccounts().get(DEST_ID).getBalance());
	}

	@Test
	public void transfer_from_savings_to_savings_with_valid_parameters() {
		SavingsAccount source = new SavingsAccount(DEFAULT_APR);
		SavingsAccount dest = new SavingsAccount(DEFAULT_APR);

		bank.addAccount(SOURCE_ID, source);
		bank.addAccount(DEST_ID, dest);

		bank.deposit(SOURCE_ID, 300);

		commandHandler.handle(TRANSFER_ACTION + " " + SOURCE_ID + " " + DEST_ID + " 200");

		assertEquals(100, bank.getAccounts().get(SOURCE_ID).getBalance());
		assertEquals(200, bank.getAccounts().get(DEST_ID).getBalance());
	}

	@Test
	public void transfer_more_than_source_balance_transfers_remaining_balance() {
		CheckingAccount source = new CheckingAccount(DEFAULT_APR);
		CheckingAccount dest = new CheckingAccount(DEFAULT_APR);

		bank.addAccount(SOURCE_ID, source);
		bank.addAccount(DEST_ID, dest);

		bank.deposit(SOURCE_ID, 200);

		commandHandler.handle(TRANSFER_ACTION + " " + SOURCE_ID + " " + DEST_ID + " 300");

		assertEquals(0, bank.getAccounts().get(SOURCE_ID).getBalance());
		assertEquals(200, bank.getAccounts().get(DEST_ID).getBalance());
	}

	@Test
	public void transfer_from_empty_account_transfers_zero() {
		CheckingAccount source = new CheckingAccount(DEFAULT_APR);
		CheckingAccount dest = new CheckingAccount(DEFAULT_APR);

		bank.addAccount(SOURCE_ID, source);
		bank.addAccount(DEST_ID, dest);

		commandHandler.handle(TRANSFER_ACTION + " " + SOURCE_ID + " " + DEST_ID + " 200");

		assertEquals(0, bank.getAccounts().get(SOURCE_ID).getBalance());
		assertEquals(0, bank.getAccounts().get(DEST_ID).getBalance());
	}

	@Test
	public void transfer_zero_amount_does_not_change_balances() {
		CheckingAccount source = new CheckingAccount(DEFAULT_APR);
		CheckingAccount dest = new CheckingAccount(DEFAULT_APR);

		bank.addAccount(SOURCE_ID, source);
		bank.addAccount(DEST_ID, dest);

		bank.deposit(SOURCE_ID, 200);

		commandHandler.handle(TRANSFER_ACTION + " " + SOURCE_ID + " " + DEST_ID + " 0");

		assertEquals(200, bank.getAccounts().get(SOURCE_ID).getBalance());
		assertEquals(0, bank.getAccounts().get(DEST_ID).getBalance());
	}
}
