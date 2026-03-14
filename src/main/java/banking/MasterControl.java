package banking;

import java.util.ArrayList;
import java.util.List;

public class MasterControl {
	private final CommandValidator commandValidator;
	private final CommandHandler commandHandler;
	private final CommandStorage commandStorage;
	private final TransactionHistory transactionHistory;

	public MasterControl(CommandValidator commandValidator, CommandHandler commandHandler,
			CommandStorage commandStorage, TransactionHistory transactionHistory) {
		this.commandValidator = commandValidator;
		this.commandHandler = commandHandler;
		this.commandStorage = commandStorage;
		this.transactionHistory = transactionHistory;
	}

	public List<String> start(List<String> input) {
		for (String command : input) {
			if (commandValidator.validate(command)) {
				commandHandler.handle(command);
				commandStorage.addValidCommand(command);
			} else {
				commandStorage.addInvalidCommand(command);
			}
		}

		return transactionHistory.formatOutput();
	}
}
