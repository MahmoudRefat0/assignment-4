// base class for all account types
public class Account {
    private String accountNumber;
    private String customerId;
    private double balance;
    private String status; // ACTIVE, FROZEN, CLOSED
    private int transactionCount;

    // constructor
    public Account(String accountNumber, String customerId, double balance) {
        this.accountNumber = accountNumber;
        this.customerId = customerId;
        this.balance = balance;
        this.status = "ACTIVE";
        this.transactionCount = 0;
    }

    // getters and setters
    public String getAccountNumber() {
        return accountNumber;
    }

    public String getCustomerId() {
        return customerId;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getTransactionCount() {
        return transactionCount;
    }

    // deposit method
    public boolean deposit(double amount) {
        if (!status.equalsIgnoreCase("ACTIVE")) {
            System.out.println("Error: Account is " + status);
            return false;
        }
        if (amount <= 0) {
            System.out.println("Error: Amount must be positive.");
            return false;
        }
        balance += amount;
        transactionCount++;
        return true;
    }

    // basic withdraw method to be overridden
    public boolean withdraw(double amount) {
        if (!status.equalsIgnoreCase("ACTIVE")) {
            System.out.println("Error: Account is " + status);
            return false;
        }
        if (amount <= 0) {
            System.out.println("Error: Amount must be positive.");
            return false;
        }
        if (balance >= amount) {
            balance -= amount;
            transactionCount++;
            return true;
        } else {
            System.out.println("Error: Insufficient balance.");
            return false;
        }
    }

    // display basic details
    public void printAccountInfo() {
        System.out.println("Account No: " + accountNumber);
        System.out.println("Customer ID: " + customerId);
        System.out.println("Balance: $" + balance);
        System.out.println("Status: " + status);
        System.out.println("Transactions: " + transactionCount);
    }
}