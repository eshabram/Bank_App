public class Customer {
    private String name;
    private String address;
    private int zip;
    private String ssn;


    public Customer() {
        this.name = "Default";
        this.address = "0 Nowheresville";
        this.zip = 00000;
        this.ssn = "";
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
