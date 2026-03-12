package banking;

public class WithdrawHandler extends BaseHandler {
	public WithdrawHandler(Bank bank) {
		super(bank);
	}

	public void handle(String command) {
		String quickId = command.split(" ")[1];
		double balance = Double.parseDouble(command.split(" ")[2]);
		bank.withdraw(quickId, balance);
	}
}
