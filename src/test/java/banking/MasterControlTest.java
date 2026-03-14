package banking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MasterControlTest {
	MasterControl masterControl;
	List<String> input;

	@BeforeEach
	void setUp() {
		input = new ArrayList<>();
		Bank bank = new Bank();
		CommandStorage commandStorage = new CommandStorage();
		masterControl = new MasterControl(new CommandValidator(bank), new CommandHandler(bank), commandStorage,
				new TransactionHistory(bank, commandStorage));
	}

	@Test
	void sample_make_sure_this_passes_unchanged_or_you_will_fail() {
		input.add("Create savings 12345678 0.6");
		input.add("Deposit 12345678 700");
		input.add("Deposit 12345678 5000");
		input.add("creAte cHecKing 98765432 0.01");
		input.add("Deposit 98765432 300");
		input.add("Transfer 98765432 12345678 300");
		input.add("Pass 1");
		input.add("Create cd 23456789 1.2 2000");
		List<String> actual = masterControl.start(input);

		assertEquals(5, actual.size());
		assertEquals("Savings 12345678 1000.50 0.60", actual.get(0));
		assertEquals("Deposit 12345678 700", actual.get(1));
		assertEquals("Transfer 98765432 12345678 300", actual.get(2));
		assertEquals("Cd 23456789 2000.00 1.20", actual.get(3));
		assertEquals("Deposit 12345678 5000", actual.get(4));
	}

	@Test
	void checking_deposit_limit_behavior() {
		input.add("Create checking 12345678 0.6");
		input.add("Deposit 12345678 1000");
		input.add("Deposit 12345678 1500"); // invalid
		input.add("Pass 1");

		List<String> actual = masterControl.start(input);

		assertEquals(3, actual.size());
		assertEquals("Checking 12345678 1000.50 0.60", actual.get(0));
		assertEquals("Deposit 12345678 1000", actual.get(1));
		assertEquals("Deposit 12345678 1500", actual.get(2));
	}

	@Test
	void transfer_between_accounts_records_for_source_and_destination() {
		input.add("Create checking 12345678 0.6");
		input.add("Create savings 87654321 0.6");
		input.add("Deposit 12345678 400");
		input.add("Transfer 12345678 87654321 300");

		List<String> actual = masterControl.start(input);

		assertEquals(5, actual.size());
		assertEquals("Checking 12345678 100.00 0.60", actual.get(0));
		assertEquals("Deposit 12345678 400", actual.get(1));
		assertEquals("Transfer 12345678 87654321 300", actual.get(2));
		assertEquals("Savings 87654321 300.00 0.60", actual.get(3));
		assertEquals("Transfer 12345678 87654321 300", actual.get(4));
	}

	@Test
	void transfer_between_accounts_records_for_active_accounts() {
		input.add("Create checking 12345678 0.6");
		input.add("Create savings 87654321 0.6");
		input.add("Deposit 12345678 1000");
		input.add("Deposit 12345678 200");
		input.add("Transfer 12345678 87654321 200");
		input.add("Withdraw 87654321 200");
		input.add("Pass 1");

		List<String> actual = masterControl.start(input);

		assertEquals(4, actual.size());
		assertEquals("Checking 12345678 1000.50 0.60", actual.get(0));
		assertEquals("Deposit 12345678 1000", actual.get(1));
		assertEquals("Deposit 12345678 200", actual.get(2));
		assertEquals("Transfer 12345678 87654321 200", actual.get(3));
	}

	@Test
	void only_show_history_for_active_account_with_same_id_as_closed_account() {
		input.add("Create checking 12345678 0.6");
		input.add("Create savings 87654321 0.6");
		input.add("Deposit 12345678 1000");
		input.add("Deposit 12345678 200");
		input.add("Transfer 12345678 87654321 200");
		input.add("Withdraw 87654321 200");
		input.add("Pass 1");
		input.add("Create savings 87654321 0.6");
		input.add("Deposit 87654321 300");

		List<String> actual = masterControl.start(input);

		assertEquals(6, actual.size());
		assertEquals("Checking 12345678 1000.50 0.60", actual.get(0));
		assertEquals("Deposit 12345678 1000", actual.get(1));
		assertEquals("Deposit 12345678 200", actual.get(2));
		assertEquals("Transfer 12345678 87654321 200", actual.get(3));
		assertEquals("Savings 87654321 300.00 0.60", actual.get(4));
		assertEquals("Deposit 87654321 300", actual.get(5));
	}

	@Test
	void minimum_balance_fee_applied_after_pass_time() {
		input.add("Create savings 12345678 0.6");
		input.add("Deposit 12345678 50");
		input.add("Pass 1");

		List<String> actual = masterControl.start(input);

		assertEquals(2, actual.size());
		assertEquals("Savings 12345678 25.01 0.60", actual.get(0));
		assertEquals("Deposit 12345678 50", actual.get(1));
	}

	@Test
	void account_closed_when_balance_zero_after_pass() {
		input.add("Create checking 12345678 0.6");
		input.add("Deposit 12345678 50");
		input.add("Withdraw 12345678 50");
		input.add("Pass 1");

		List<String> actual = masterControl.start(input);

		assertEquals(0, actual.size());
	}

	@Test
	void cd_account_remains_without_transactions() {
		input.add("Create cd 12345678 1.2 2000");

		List<String> actual = masterControl.start(input);

		assertEquals(1, actual.size());
		assertEquals("Cd 12345678 2000.00 1.20", actual.get(0));
	}

	@Test
	void typo_in_create_command_is_invalid() {
		input.add("creat checking 12345678 1.0");

		List<String> actual = masterControl.start(input);
		assertSingleCommand("creat checking 12345678 1.0", actual);
	}

	@Test
	void typo_in_deposit_command_is_invalid() {
		input.add("depositt checking 12345678 1.0");

		List<String> actual = masterControl.start(input);
		assertSingleCommand("depositt checking 12345678 1.0", actual);
	}

	@Test
	void two_typo_commands_both_invalid() {
		input.add("creat checking 12345678 1.0");
		input.add("depositt checking 12345678 1.0");

		List<String> actual = masterControl.start(input);

		assertEquals(2, actual.size());
		assertEquals("creat checking 12345678 1.0", actual.get(0));
		assertEquals("depositt checking 12345678 1.0", actual.get(1));
	}

	@Test
	void invalid_to_create_accounts_with_same_ID() {
		input.add("create checking 12345678 1.0");
		input.add("create checking 12345678 1.0");

		List<String> actual = masterControl.start(input);
		assertEquals("Checking 12345678 0.00 1.00", actual.get(0));
		assertEquals("create checking 12345678 1.0", actual.get(1));
	}

	private static void assertSingleCommand(String command, List<String> actual) {
		assertEquals(1, actual.size());
		assertEquals(command, actual.get(0));
	}

}
