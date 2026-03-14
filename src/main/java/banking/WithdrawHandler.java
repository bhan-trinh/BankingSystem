package banking;

public class WithdrawHandler extends BaseHandler {
	public WithdrawHandler(Bank bank) {
		super(bank);
	}

	@Override
	protected void bankAction(String quickId, double balance) {
		bank.withdraw(quickId, balance);
	}
}
