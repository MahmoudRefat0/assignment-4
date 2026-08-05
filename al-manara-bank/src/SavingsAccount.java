// savings account child class
public class SavingsAccount extends Account {
    private double annualInterestRate;
    private int monthlyWithdrawals;

    public SavingsAccount(String accountNumber, String customerId, double balance, double annualInterestRate) {
        super(accountNumber, customerId, balance);
        this.annualInterestRate = annualInterestRate;
        this.monthlyWithdrawals = 0;
    }

    @Override
    public boolean withdraw(double amount) {
        // checks savings rule (balance cannot become negative)
        if (getBalance() - amount < 0) {
            System.out.println("Error: Savings account balance cannot be negative.");
            return false;
        }
        boolean success = super.withdraw(amount);
        if (success) {
            monthlyWithdrawals++;
        }
        return success;
    }

    @Override
    public void printAccountInfo() {
        System.out.println("Type: Savings Account");
        super.printAccountInfo();
        System.out.println("Interest Rate: " + annualInterestRate + "%");
        System.out.println("Monthly Withdrawals: " + monthlyWithdrawals);
    }
}