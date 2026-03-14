package banking;

public abstract class Account {
	private double balance;
	private final double apr;
	private String id;

	Account(double apr, double balance) {
		this.balance = balance;
		this.apr = apr;
	}

	public double getBalance() {
		return balance;
	}

	public void deposit(double amount) {
		balance += amount;
	}

	public double withdraw(double amount) {
		if (amount > balance) {
			amount = balance;
			balance = 0;
			return amount;
		} else {
			balance -= amount;
			return amount;
		}
	}

	public double getAPR() {
		return apr;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}

	public abstract boolean isDepositable();

	public abstract boolean isWithdrawable();

	public abstract boolean isTransferable();

	public abstract double getMaxDepositAmount();

	public abstract double getMaxWithdrawAmount();

	public abstract double getMinWithdrawAmount();

	public void accrue(double amount) {
		balance += amount;
	}

	public void deduct(double amount) {
		if (amount > balance) {
			balance = 0;
		} else {
			balance -= amount;
		}
	}

	public void applyAPR() {
		double amountAccrued = this.getAPR() / 100 / 12 * this.getBalance();
		this.accrue(amountAccrued);
	}

	public abstract void processMonth();
}
