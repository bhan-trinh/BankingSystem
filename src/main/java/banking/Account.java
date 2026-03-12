package banking;

public abstract class Account {
	private double balance;
	private final double apr;
	private String id;
	private final double maxDepositLimit;
	private final double maxWithdrawLimit;
	private final boolean isDepositable;

	Account(double apr, double balance, boolean isDepositable, double depositLimit, double withdrawLimit) {
		this.balance = balance;
		this.apr = apr;
		this.maxDepositLimit = depositLimit;
		this.maxWithdrawLimit = withdrawLimit;
		this.isDepositable = isDepositable;
	}

	public double getBalance() {
		return balance;
	}

	public void deposit(double v) {
		balance += v;
	}

	public void withdraw(double v) {
		if (v > balance) {
			balance = 0;
		} else {
			balance -= v;
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

	public double getMaxDepositAmount() {
		return maxDepositLimit;
	}

	public double getMaxWithdrawAmount() {
		return maxWithdrawLimit;
	}

	public double getMinWithdrawAmount() {
		return 0;
	}
}
