// class to store customer data
public class Customer {
    private String customerId;
    private String fullName;
    private String nationalId;
    private String phone;
    private CustomerTier tier; // STANDARD, SILVER, GOLD
    private int accountCount; // counter for customer accounts

    // constructor
    public Customer(String customerId, String fullName, String nationalId, String phone, CustomerTier tier) {
        this.customerId = customerId;
        this.fullName = fullName;
        this.nationalId = nationalId;
        this.phone = phone;
        this.tier = tier;
        this.accountCount = 0;
    }

    // getters and setters
    public String getCustomerId() {
        return customerId;
    }

    public String getFullName() {
        return fullName;
    }

    public String getNationalId() {
        return nationalId;
    }

    public String getPhone() {
        return phone;
    }

    public CustomerTier getTier() {
        return tier;
    }

    public int getAccountCount() {
        return accountCount;
    }

    public void incrementAccountCount() {
        this.accountCount++;
    }

    public void decrementAccountCount() {
        if (this.accountCount > 0) {
            this.accountCount--;
        }
    }

    // display customer info
    public void printCustomerInfo() {
        System.out.println("Customer ID: " + customerId);
        System.out.println("Name: " + fullName);
        System.out.println("National ID: " + nationalId);
        System.out.println("Phone: " + phone);
        System.out.println("Tier: " + tier);
        System.out.println("Open Accounts: " + accountCount);
    }
}