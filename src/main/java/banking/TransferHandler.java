package banking;

public class TransferHandler extends BaseHandler {
	private final DepositHandler depositHandler;
	private final WithdrawHandler withdrawHandler;

	public TransferHandler(Bank bank, DepositHandler depositHandler, WithdrawHandler withdrawHandler) {
		super(bank);
		this.depositHandler = depositHandler;
		this.withdrawHandler = withdrawHandler;
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
