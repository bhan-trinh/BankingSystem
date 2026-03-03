package banking;

import java.util.ArrayList;
import java.util.List;

public class CommandStorage {
	List<String> invalidCommands = new ArrayList<>();

	CommandStorage() {

	}

	public void addInvalidCommand(String invalidCommand) {
		invalidCommands.add(invalidCommand);
	}

	public List<String> getInvalidCommands() {
		return invalidCommands;
	}
}
