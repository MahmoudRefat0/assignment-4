import java.util.Scanner;

public class Main {
    // static arrays for data storage as required
    static Customer[] customers = new Customer[100];
    static Account[] accounts = new Account[200];

    static int customerCount = 0;
    static int accountCount = 0;

    static int nextCustomerId = 1001;
    static int nextAccountNumber = 5001;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int choice = -1;

        while (choice != 0) {
            printMenu();
            System.out.print("Enter your choice: ");

            // input validation for menu
            if (!sc.hasNextInt()) {
                System.out.println("Invalid input! Please enter a number.");
                sc.next();
                continue;
            }
            choice = sc.nextInt();
            sc.nextLine(); // clear buffer

            switch (choice) {
                case 1:
                    registerCustomer(sc);
                    break;
                case 2:
                    openAccount(sc);
                    break;
                case 3:
                    depositMoney(sc);
                    break;
                case 4:
                    withdrawMoney(sc);
                    break;
                case 5:
                    transferMoney(sc);
                    break;
                case 6:
                    displayCustomerAccounts(sc);
                    break;
                case 7:
                    displayAllAccounts();
                    break;
                case 8:
                    searchAccountByNumber(sc);
                    break;
                case 9:
                    searchAccountsByType(sc);
                    break;
                case 10:
                    closeAccount(sc);
                    break;
                case 0:
                    System.out.println("Thank you for using Al Manara Bank System. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid option! Try again.");
            }
            System.out.println("------------------------------------------");
        }
        sc.close();
    }

    static void printMenu() {
        System.out.println("\n=== AL MANARA BANK MAIN MENU ===");
        System.out.println("1. Register New Customer");
        System.out.println("2. Open New Account");
        System.out.println("3. Deposit Money");
        System.out.println("4. Withdraw Money");
        System.out.println("5. Transfer Between Accounts");
        System.out.println("6. Display Customer Accounts");
        System.out.println("7. Display All Branch Accounts");
        System.out.println("8. Search Account by Number");
        System.out.println("9. Search Accounts by Type");
        System.out.println("10. Close an Account");
        System.out.println("0. Exit");
    }

    // 1. Register Customer
    static void registerCustomer(Scanner sc) {
        if (customerCount >= customers.length) {
            System.out.println("Storage full! Cannot add more customers.");
            return;
        }

        System.out.print("Enter Full Name: ");
        String name = sc.nextLine();
        if (name.trim().isEmpty()) {
            System.out.println("Error: Name cannot be empty.");
            return;
        }

        System.out.print("Enter National ID: ");
        String nationalId = sc.nextLine();

        // check duplicate national id
        for (int i = 0; i < customerCount; i++) {
            if (customers[i].getNationalId().equals(nationalId)) {
                System.out.println("Error: National ID already registered!");
                return;
            }
        }

        System.out.print("Enter Phone (optional): ");
        String phone = sc.nextLine();
        if (!phone.isEmpty() && (!phone.matches("\\d+") || phone.length() < 7 || phone.length() > 15)) {
            System.out.println("Error: Invalid phone number.");
            return;
        }

        System.out.print("Enter Tier (1: STANDARD, 2: SILVER, 3: GOLD): ");
        int tierChoice = sc.nextInt();
        sc.nextLine();
        CustomerTier tier = CustomerTier.STANDARD;
        if (tierChoice == 2) tier = CustomerTier.SILVER;
        if (tierChoice == 3) tier = CustomerTier.GOLD;

        String id = "C" + nextCustomerId++;
        Customer c = new Customer(id, name, nationalId, phone, tier);
        customers[customerCount++] = c;

        System.out.println("Customer registered successfully. Customer ID: " + id);
    }

    // 2. Open Account
    static void openAccount(Scanner sc) {
        if (accountCount >= accounts.length) {
            System.out.println("Storage full! Cannot open more accounts.");
            return;
        }

        System.out.print("Enter Customer ID: ");
        String cid = sc.nextLine();
        Customer cust = findCustomer(cid);
        if (cust == null) {
            System.out.println("Error: Customer ID not found.");
            return;
        }

        System.out.println("Select Account Type: 1- Savings, 2- Current, 3- Fixed Deposit");
        int type = sc.nextInt();
        System.out.print("Enter Initial Deposit: ");
        double balance = sc.nextDouble();
        sc.nextLine();

        if (balance < 0) {
            System.out.println("Error: Deposit amount cannot be negative.");
            return;
        }

        String accNo = "ACC" + nextAccountNumber++;
        Account acc = null;

        if (type == 1) {
            acc = new SavingsAccount(accNo, cid, balance, 5.0); // 5% interest
        } else if (type == 2) {
            acc = new CurrentAccount(accNo, cid, balance, 1000.0); // $1000 overdraft
        } else if (type == 3) {
            System.out.print("Enter duration in months: ");
            int months = sc.nextInt();
            sc.nextLine();
            acc = new FixedDepositAccount(accNo, cid, balance, months, 8.0); // 8% interest
        } else {
            System.out.println("Invalid account type.");
            return;
        }

        accounts[accountCount++] = acc;
        cust.incrementAccountCount();
        System.out.println("Account opened successfully! Account Number: " + accNo);
    }

    // 3. Deposit
    static void depositMoney(Scanner sc) {
        System.out.print("Enter Account Number: ");
        String accNo = sc.nextLine();
        Account acc = findAccount(accNo);

        if (acc == null) {
            System.out.println("Error: Account not found.");
            return;
        }

        System.out.print("Enter Deposit Amount: ");
        double amount = sc.nextDouble();
        sc.nextLine();

        if (acc.deposit(amount)) {
            System.out.println("Deposit successful! New Balance: $" + acc.getBalance());
        }
    }

    // 4. Withdraw
    static void withdrawMoney(Scanner sc) {
        System.out.print("Enter Account Number: ");
        String accNo = sc.nextLine();
        Account acc = findAccount(accNo);

        if (acc == null) {
            System.out.println("Error: Account not found.");
            return;
        }

        System.out.print("Enter Withdrawal Amount: ");
        double amount = sc.nextDouble();
        sc.nextLine();

        if (acc.withdraw(amount)) {
            System.out.println("Withdrawal successful! New Balance: $" + acc.getBalance());
        }
    }

    // 5. Transfer Money
    static void transferMoney(Scanner sc) {
        System.out.print("Enter Source Account Number: ");
        String srcNo = sc.nextLine();
        System.out.print("Enter Destination Account Number: ");
        String destNo = sc.nextLine();

        if (srcNo.equalsIgnoreCase(destNo)) {
            System.out.println("Error: Source and destination accounts must be different.");
            return;
        }

        Account src = findAccount(srcNo);
        Account dest = findAccount(destNo);

        if (src == null || dest == null) {
            System.out.println("Error: One or both accounts do not exist.");
            return;
        }

        System.out.print("Enter Transfer Amount: ");
        double amount = sc.nextDouble();
        sc.nextLine();

        // process transfer atomically
        if (src.withdraw(amount)) {
            if (dest.deposit(amount)) {
                System.out.println("Transfer successful!");
            } else {
                // rollback if deposit fails
                src.setBalance(src.getBalance() + amount);
                System.out.println("Transfer failed! Funds restored to source account.");
            }
        }
    }

    // 6. Display Customer Accounts
    static void displayCustomerAccounts(Scanner sc) {
        System.out.print("Enter Customer ID: ");
        String cid = sc.nextLine();
        Customer cust = findCustomer(cid);

        if (cust == null) {
            System.out.println("Error: Customer not found.");
            return;
        }

        cust.printCustomerInfo();
        System.out.println("--- Customer Accounts ---");
        double totalBalance = 0;
        for (int i = 0; i < accountCount; i++) {
            if (accounts[i].getCustomerId().equalsIgnoreCase(cid)) {
                accounts[i].printAccountInfo();
                totalBalance += accounts[i].getBalance();
                System.out.println("----------------");
            }
        }
        System.out.println("Combined Total Balance: $" + totalBalance);
    }

    // 7. Display All Accounts
    static void displayAllAccounts() {
        if (accountCount == 0) {
            System.out.println("No accounts found in the branch.");
            return;
        }
        for (int i = 0; i < accountCount; i++) {
            accounts[i].printAccountInfo();
            System.out.println("----------------");
        }
    }

    // 8. Search Account by Number
    static void searchAccountByNumber(Scanner sc) {
        System.out.print("Enter Account Number: ");
        String accNo = sc.nextLine();
        Account acc = findAccount(accNo);

        if (acc != null) {
            acc.printAccountInfo();
        } else {
            System.out.println("Account not found.");
        }
    }

    // 9. Search Accounts by Type
    static void searchAccountsByType(Scanner sc) {
        System.out.println("Select Type: 1- Savings, 2- Current, 3- Fixed Deposit");
        int type = sc.nextInt();
        sc.nextLine();

        int count = 0;
        double sum = 0;

        for (int i = 0; i < accountCount; i++) {
            boolean match = false;
            if (type == 1 && accounts[i] instanceof SavingsAccount) match = true;
            if (type == 2 && accounts[i] instanceof CurrentAccount) match = true;
            if (type == 3 && accounts[i] instanceof FixedDepositAccount) match = true;

            if (match) {
                accounts[i].printAccountInfo();
                System.out.println("----------------");
                count++;
                sum += accounts[i].getBalance();
            }
        }
        System.out.println("Total Matching Accounts: " + count);
        System.out.println("Combined Balance: $" + sum);
    }

    // 10. Close Account
    static void closeAccount(Scanner sc) {
        System.out.print("Enter Account Number to close: ");
        String accNo = sc.nextLine();
        Account acc = findAccount(accNo);

        if (acc == null) {
            System.out.println("Error: Account not found.");
            return;
        }

        if (acc.getStatus() == AccountStatus.CLOSED) {
            System.out.println("Error: Account is already closed.");
            return;
        }

        if (acc.getBalance() != 0) {
            System.out.println("Error: Account balance must be exactly $0 to close.");
            return;
        }

        acc.setStatus(AccountStatus.CLOSED);
        Customer c = findCustomer(acc.getCustomerId());
        if (c != null) {
            c.decrementAccountCount();
        }
        System.out.println("Account closed successfully!");
    }

    // Helper method: search customer by ID
    static Customer findCustomer(String id) {
        for (int i = 0; i < customerCount; i++) {
            if (customers[i].getCustomerId().equalsIgnoreCase(id)) {
                return customers[i];
            }
        }
        return null;
    }

    // Helper method: search account by number
    static Account findAccount(String accNo) {
        for (int i = 0; i < accountCount; i++) {
            if (accounts[i].getAccountNumber().equalsIgnoreCase(accNo)) {
                return accounts[i];
            }
        }
        return null;
    }
}