// current account with overdraft feature
public class CurrentAccount extends Account {
    private double overdraftLimit;

    public CurrentAccount(String accountNumber, String customerId, double balance, double overdraftLimit) {
        super(accountNumber, customerId, balance);
    }

    @Override
    public boolean withdraw(double amount) {
        if (getStatus() != AccountStatus.ACTIVE) {
            System.out.println("Error: Account is " + getStatus());
            return false;
        }
        if (amount <= 0) {
            System.out.println("Error: Amount must be positive.");
            return false;
        }

        // check if withdrawal exceeds balance + overdraft
        if (getBalance() - amount < -overdraftLimit) {
            System.out.println("Error: Exceeds overdraft limit of $" + overdraftLimit);
            return false;
        }

        setBalance(getBalance() - amount);
        return true;
    }

    @Override
    public void printAccountInfo() {
        System.out.println("Type: Current Account");
        super.printAccountInfo();
        System.out.println("Overdraft Limit: $" + overdraftLimit);
        if (getBalance() < 0) {
            System.out.println("Status Notice: Currently using overdraft.");
        }
    }
}