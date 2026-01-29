public abstract class Account {
	private float balance;
	private final float apr;
	private String id;

	public Account(float apr, float balance) {
		this.balance = balance;
		this.apr = apr;
	}

	public float getBalance() {
		return balance;
	}

	public void deposit(float v) {
		balance += v;
	}

	public void withdraw(float v) {
		if (v > balance) {
			balance = 0;
		} else {
			balance -= v;
		}
	}

	public float getAPR() {
		return apr;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}
}
