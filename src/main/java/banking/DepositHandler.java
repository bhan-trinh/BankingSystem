package banking;

public class DepositHandler extends BaseHandler {
	public DepositHandler(Bank bank) {
		super(bank);
	}

	@Override
	protected void bankAction(String quickId, double balance) {
		bank.deposit(quickId, balance);
	}
}
