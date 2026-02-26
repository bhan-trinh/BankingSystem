public class CommandHandler {
	Bank bank;
	BaseHandler handler;

	public CommandHandler(Bank bank) {
		this.bank = bank;
	}

	public void handle(String command) {
		String action = command.split(" ")[0].toLowerCase();

		switch (action) {
		case "create":
			handler = new CreateHandler(bank);
			break;
		case "deposit":
			handler = new DepositHandler(bank);
			break;
		}

		handler.handle(command);
	}
}
