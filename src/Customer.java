import java.util.ArrayList;

public class Customer {
    private String name;
    private String address;
    private int zip;
    private String ssn;
    private boolean savings = false;
    private boolean checking = false;
    private int checkingNum;
    private int savingsNum;

    public Customer() {
        name = "Default";
        address = "0 Nowheresville";
        zip = 00000;
        ssn = "";
    }

    public Customer(String name, String addr, int zip, String ssn) {
        this.name = name;
        this.address = addr;
        this.zip = zip;
        this.ssn = ssn;
    }

    public String getName() {
        return name;
    }
    public String getAddress() {
        return address;
    }
    public int getZip() {
        return zip;
    }
    public String getSsn() {
        return ssn;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setAddress(String addr) {
        this.address = addr;
    }
    public void setZip(int zip) {
        this.zip = zip;
    }
    public void setSsn(String ssn) {
        this.ssn = ssn;
    }

    public boolean hasChecking() {
        if (checking) {
            return true;
        } else return false;
    }
    public boolean hasSavings() {
        if (savings) {
            return true;
        } else return false;
    }
    public void setChecking(boolean bool, int accNum) {
        checking = bool;
        checkingNum = accNum;
    }
    public void setSavings(boolean bool, int accNum) {
        savings = bool;
        savingsNum = accNum;
    }
    public int getCheckingNum() {
        return checkingNum;
    }
    public int getSavingsNum() {
        return savingsNum;
    }
    public boolean equals(Customer cust) {
        if (this.name.equals(cust.getName()) && this.address.equals(cust.getAddress()) &&
        this.zip == cust.getZip() && this.ssn.equals(cust.getSsn())) {
            return true;
        } else return false;
    }

    public String toString() {
        return String.format("%s, %s, %d, %s", name, address, zip, ssn);
    }
}
