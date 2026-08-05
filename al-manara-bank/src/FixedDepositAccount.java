// fixed deposit account class
public class FixedDepositAccount extends Account {
    private double interestRate;
    private int durationMonths;
    private int elapsedMonths;

    public FixedDepositAccount(String accountNumber, String customerId, double balance, int durationMonths, double interestRate) {
        super(accountNumber, customerId, balance);
        this.durationMonths = durationMonths;
        this.interestRate = interestRate;
        this.elapsedMonths = 0; // starts at 0 months
    }

    public void passMonths(int months) {
        this.elapsedMonths += months;
    }

    @Override
    public boolean withdraw(double amount) {
        // check maturity
        if (elapsedMonths < durationMonths) {
            int remaining = durationMonths - elapsedMonths;
            System.out.println("Error: Fixed Deposit not matured yet. Remaining months: " + remaining);
            return false;
        }
        return super.withdraw(amount);
    }

    @Override
    public void printAccountInfo() {
        System.out.println("Type: Fixed Deposit Account");
        super.printAccountInfo();
        System.out.println("Duration: " + durationMonths + " months");
        System.out.println("Elapsed: " + elapsedMonths + " months");
        System.out.println("Is Matured: " + (elapsedMonths >= durationMonths));
    }
}