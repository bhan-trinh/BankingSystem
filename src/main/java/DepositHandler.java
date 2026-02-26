public class DepositHandler extends BaseHandler {
	public DepositHandler(Bank bank) {
		super(bank);
	}

	public void handle(String command) {
		String quickId = command.split(" ")[1];
		double balance = Double.parseDouble(command.split(" ")[2]);
		bank.deposit(quickId, balance);
	}
}
