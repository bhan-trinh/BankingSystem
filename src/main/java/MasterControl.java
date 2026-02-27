import java.util.List;

public class MasterControl {
	private final CommandValidator commandValidator;
	private final CommandHandler commandHandler;
	private final CommandStorage commandStorage;

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
