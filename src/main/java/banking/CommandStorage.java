package banking;

import java.util.ArrayList;
import java.util.List;

public class CommandStorage {
	List<String> validCommands = new ArrayList<>();
	List<String> invalidCommands = new ArrayList<>();

	CommandStorage() {

	}

	public void addInvalidCommand(String invalidCommand) {
		invalidCommands.add(invalidCommand);
	}

	public void addValidCommand(String validCommand) {
		validCommands.add(validCommand);
	}

	public List<String> getInvalidCommands() {
		return invalidCommands;
	}

	public List<String> getValidCommandsOfAccount(String accountId) {
		List<String> validCommandsOfAccount = new ArrayList<>();
		boolean isCreated = false;

		for (String command : validCommands) {
			if (command.contains(accountId)) {
				if (command.toLowerCase().contains("create")) {
					if (isCreated) {
						validCommandsOfAccount = new ArrayList<>();
					}
					isCreated = true;
				}
				validCommandsOfAccount.add(command);
			}
		}
		return validCommandsOfAccount;
	}
}
