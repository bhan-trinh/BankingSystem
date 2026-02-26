import java.util.List;

public class CommandStorage {
	List<String> invalidCommands;

	CommandStorage() {

	}

	public void addInvalidCommand(String invalidCommand) {
		invalidCommands.add(invalidCommand);
	}

	public List<String> getInvalidCommands() {
		return invalidCommands;
	}
}
