package banking;

public class TransferHandler extends BaseHandler {
	public TransferHandler(Bank bank) {
		super(bank);
	}

	@Override
	public void handle(String command) {

		String srcId = command.split(" ")[1];
		String desId = command.split(" ")[2];
		double amount = Double.parseDouble(command.split(" ")[3]);

		double amountWithdrawn = bank.withdraw(srcId, amount);
		bank.deposit(desId, amountWithdrawn);
	}
}
