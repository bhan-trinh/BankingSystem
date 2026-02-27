import java.util.List;

public class MasterControl {
	private CommandValidator commandValidator;
	private CommandHandler commandHandler;
	private CommandStorage commandStorage;

	public MasterControl(CommandValidator commandValidator, CommandHandler commandHandler,
			CommandStorage commandStorage) {
		this.commandValidator = commandValidator;
		this.commandHandler = commandHandler;
		this.commandStorage = commandStorage;
	}

	public List<String> start(List<String> input) {
		for (String command : input) {
			if (commandValidator.validate(command)) {
				commandHandler.handle(command);
			} else {
				commandStorage.addInvalidCommand(command);
			}
		}
		return commandStorage.getInvalidCommands();
	}
}
