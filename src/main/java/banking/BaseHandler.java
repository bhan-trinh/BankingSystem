package banking;

public abstract class BaseHandler {
	Bank bank;

	protected BaseHandler(Bank bank) {
		this.bank = bank;
	}

	public void handle(String command) {
		String quickId = command.split(" ")[1];
		double balance = Double.parseDouble(command.split(" ")[2]);
		bankAction(quickId, balance);
	}

	protected void bankAction(String quickId, double balance) {
		// override by deposit and withdraw
	}
}
