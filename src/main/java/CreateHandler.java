public class CreateHandler extends BaseHandler {
	public CreateHandler(Bank bank) {
		super(bank);
	}

	public void handle(String command) {
		CheckingAccount checkingAccount = new CheckingAccount(1);
		bank.addAccount("12345678", checkingAccount);
	}
}
