package banking;

public class CreateHandler extends BaseHandler {
	public CreateHandler(Bank bank) {
		super(bank);
	}

	public void handle(String command) {
		String accountType = command.split(" ")[1].toLowerCase();
		String quickId = command.split(" ")[2];
		double apr = Double.parseDouble(command.split(" ")[3]);
		double balance;
		switch (accountType) {
		case "checking":
			addCheckingAccount(quickId, apr);
			break;
		case "savings":
			addSavingsAccount(quickId, apr);
			break;
		case "cd":
			balance = Double.parseDouble(command.split(" ")[4]);
			addCDAccount(quickId, apr, balance);
			break;
		default:
			break;
		}
	}

	public void addCheckingAccount(String quickId, double apr) {
		CheckingAccount checkingAccount = new CheckingAccount(apr);
		bank.addAccount(quickId, checkingAccount);
	}

	public void addSavingsAccount(String quickId, double apr) {
		SavingsAccount savingsAccount = new SavingsAccount(apr);
		bank.addAccount(quickId, savingsAccount);
	}

	public void addCDAccount(String quickId, double apr, double balance) {
		CDAccount cdAccount = new CDAccount(apr, balance);
		bank.addAccount(quickId, cdAccount);
	}
}
