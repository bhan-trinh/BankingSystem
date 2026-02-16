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
}
