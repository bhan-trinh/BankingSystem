package banking;

public abstract class Account {
	private double balance;
	private final double apr;
	private String id;
	private final double maxDepositLimit;
	private final double maxWithdrawLimit;
	private final boolean isDepositable;
	private final boolean isTransferable;

	Account(double apr, double balance, boolean isDepositable, boolean isTransferable, double depositLimit,
			double withdrawLimit) {
		this.balance = balance;
		this.apr = apr;
		this.maxDepositLimit = depositLimit;
		this.maxWithdrawLimit = withdrawLimit;
		this.isDepositable = isDepositable;
		this.isTransferable = isTransferable;
	}

	public double getBalance() {
		return balance;
	}

	public void deposit(double amount) {
		balance += amount;
	}

	public void withdraw(double amount) {
		if (amount > balance) {
			balance = 0;
		} else {
			balance -= amount;
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

	public boolean isDepositable() {
		return isDepositable;
	}

	public boolean isWithdrawable() {
		return true;
	}

	public boolean isTransferable() {
		return isTransferable;
	}

	public double getMaxDepositAmount() {
		return maxDepositLimit;
	}

	public double getMaxWithdrawAmount() {
		return maxWithdrawLimit;
	}

	public double getMinWithdrawAmount() {
		return 0;
	}

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

	public void processMonth() {
		return;
	}
}
