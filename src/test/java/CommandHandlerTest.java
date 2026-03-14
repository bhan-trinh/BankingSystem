import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CommandHandlerTest {
	CommandHandler commandHandler;
	Bank bank;

	@BeforeEach
	public void setUp() {
		bank = new Bank();
		commandHandler = new CommandHandler(bank);
	}

	@Test
	public void checking_account_with_correct_id_and_apr_is_created() {
		// can apr be called with both 1 and 1.0
		commandHandler.handle("create checking 12345678 1.0");
		assertEquals(1.0, bank.getAccounts().get("12345678").getAPR());
	}

}
