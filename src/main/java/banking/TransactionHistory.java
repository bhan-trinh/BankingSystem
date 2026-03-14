package banking;

import java.util.ArrayList;
import java.util.List;

public class TransactionHistory {
	Bank bank;
	CommandStorage commandStorage;

	public TransactionHistory(Bank bank, CommandStorage commandStorage) {
		this.bank = bank;
		this.commandStorage = commandStorage;
	}

	public List<String> formatOutput() {
		List<String> output = new ArrayList<>();
		for (var accountEntry : bank.getAccounts().entrySet()) {
			String id = accountEntry.getKey();

			List<String> accountInfo = formatOutputOfAccount(id);
			output.addAll(accountInfo);
		}

		output.addAll(commandStorage.getInvalidCommands());
		return output;
	}

	private List<String> formatOutputOfAccount(String id) {
		List<String> outputOfAccount = new ArrayList<>();

		List<String> accountCommands = commandStorage.getValidCommandsOfAccount(id);
		String accountState = formatAccountState(id, accountCommands.get(0));
		accountCommands.remove(0);

		outputOfAccount.add(accountState);
		outputOfAccount.addAll(accountCommands);
		return outputOfAccount;
	}

	private String formatAccountState(String id, String createCommand) {
		String accountType = createCommand.split(" ")[1];
		double accountBalance = bank.getAccounts().get(id).getBalance();
		double APR = bank.getAccounts().get(id).getAPR();
		String accountStateStr = "%s %s %.2f %.2f";
		return capitalizeFirstLetter(String.format(accountStateStr, accountType, id, accountBalance, APR));
	}

	private String capitalizeFirstLetter(String str) {
		if (str == null || str.isEmpty()) {
			return str;
		}
		return Character.toUpperCase(str.charAt(0)) + str.substring(1);
	}
}
