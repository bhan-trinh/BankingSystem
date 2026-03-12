package banking;

public class PassTimeHandler extends BaseHandler {
	public PassTimeHandler(Bank bank) {
		super(bank);
	}

	public void handle(String command) {
		double month = Double.parseDouble(command.split(" ")[1]);
		bank.pass(month);
	}
}
