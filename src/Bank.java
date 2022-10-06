import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.Array;
import java.util.ArrayList;
import java.util.Scanner;
/*
Author: Elliot Shabram
Date: 10/05/2022
 */

public class Bank {
    private String name;
    private static double totalBalance = 0;
    private static int numAccounts;
    private static int numCustomers;

    // lists for customers
    private ArrayList<Customer> customers = new ArrayList<>();
    private ArrayList<String> names = new ArrayList<>();
    private ArrayList<String> ssnNumbers = new ArrayList<>();
    private ArrayList<String> addressBook = new ArrayList<>();

    // lists for accounts
    private ArrayList<String> accNames = new ArrayList<>();
    private ArrayList<Account> accounts = new ArrayList<>();
    private ArrayList<Integer> accNums = new ArrayList<>();
    private ArrayList<Transaction> transactions = new ArrayList<>();

    public Bank() {
        this.name = "Default Bank";
    }

    public Bank(String name) {
        this.name = name;
    }

    public boolean newAccount(String ssn, int accNum, int type, double balance) {
        boolean flag = false;
        if (!accNums.contains(accNum)) {
            if (ssnNumbers.contains(ssn)) {
                Customer customer = customers.get(ssnNumbers.indexOf(ssn));
                if (type == 1 && !customer.hasChecking()) {
                    Account temp = new Account(ssn, accNum, type, balance);
                    accounts.add(temp);
                    accNums.add(accNum);
                    accNames.add(customers.get(ssnNumbers.indexOf(ssn)).getName());
                    totalBalance += balance;
                    customer.setChecking(true, accNum);
                    numAccounts++;
                    flag = true;
                    System.out.printf("Account creation - Number: %d, Customer: %s%n",
                            accNum, customers.get(ssnNumbers.indexOf(ssn)).getName());
                } else if (type == 2 && !customer.hasSavings()) {
                    Account temp = new Account(ssn, accNum, type, balance);
                    accounts.add(temp);
                    accNums.add(accNum);
                    accNames.add(customers.get(ssnNumbers.indexOf(ssn)).getName());
                    totalBalance += balance;
                    numAccounts++;
                    customer.setSavings(true, accNum);
                    flag =  true;
                    System.out.printf("Account creation - Number: %d, Customer: %s%n",
                            accNum, customers.get(ssnNumbers.indexOf(ssn)).getName());
                }
                else  {
                    System.out.printf("Account creation failed - Customer %s already has two accounts%n",
                            customers.get(ssnNumbers.indexOf(ssn)).getName());
                }
            }
            else {
                System.out.printf("Account creation failed - No customer with SSN: %s%n", ssn);
            }
        }
        else {
            System.out.printf("Account creation failed - Account %d already exists%n", accNum);
        }
        return flag;
    }

    public boolean newCustomer(String name, String addr, int zip, String ssn) {
        if (!ssnNumbers.contains(ssn)) {
            Customer newCustomer = new Customer(name, addr, zip, ssn);
            customers.add(newCustomer);
            names.add(name);
            addressBook.add(addr);
            ssnNumbers.add(ssn);
            numCustomers++;
            System.out.printf("%s is added.%n", name);
            return true;
        }
        else {
            System.out.printf("%s is not added - Existing customer with matching SSN in system.%n", name);
            return false;
        }
    }

    public boolean closeAccount(int accNum) {
        if (accNums.contains(accNum)) {
            Transaction trans = new Transaction(accNum, 3, 0);
            transactions.add(trans);
            int index = accNums.indexOf(accNum);
            totalBalance -= accounts.get(index).getBalance();
            accounts.remove(index);
            accNums.remove(index);
            if (customers.get(names.indexOf(accNames.get(index))).hasChecking()) {
                customers.get(names.indexOf(accNames.get(index))).setChecking(false, 0);
            }
            else {
                customers.get(names.indexOf(accNames.get(index))).setSavings(false, 0);
            }
            accNames.remove(index);
            numAccounts--;
            return true;
        } else return false;
    }

    public boolean deposit(int accNum, double deposit) {
        Account temp = accounts.get(accNums.indexOf(accNum));
        if (accNums.contains(accNum)) {
            Transaction trans = new Transaction(accNum, 1, deposit);
            transactions.add(trans);
            temp.setBalance(temp.getBalance()+deposit);
            return true;
        } else return false;
    }
    public boolean withdraw(int accNum, double withdraw) {
        Account temp = new Account();
        temp = accounts.get(accNums.indexOf(accNum));
        if (accNums.contains(accNum)) {
            Transaction trans = new Transaction(accNum, 2, withdraw);
            transactions.add(trans);
            temp.setBalance(temp.getBalance()-withdraw);
            return true;
        } else return false;
    }

    public void transaction(int accNum) {
        boolean flag = false;
        for (Transaction transaction : transactions) {
            if (transaction.getTransAccNum() == accNum) {
                if (transaction.getTransType() == 1) {
                    System.out.printf("  - Account Number: %d, Deposit ($%.2f), %s%n", transaction.getTransAccNum(),
                            transaction.getAmount(), transaction.dateTime());
                } else if (transaction.getTransType() == 2) {
                    System.out.printf("  - Account Number: %d, Withdraw ($%.2f), %s%n", transaction.getTransAccNum(),
                            transaction.getAmount(), transaction.dateTime());
                } else {
                    System.out.printf("  - Account Number: %d, Account closed, %s%n", transaction.getTransAccNum(), transaction.dateTime());
                }
                flag = true;
            }
        }
        if (flag == false) {
            System.out.printf("  - No transaction for account %d%n", accNum);
        }
    }

    public void accountInfo(int accNum) {
        if (accNums.contains(accNum)) {
            int index = accNums.indexOf(accNum);
            Account temp = accounts.get(index);
            System.out.printf("  - Number: %d%n", accNum);
            if (temp.getType() == 1) {
                System.out.println("  - Checking");
            } else System.out.println("  - Savings");
            System.out.printf("  - Balance: $%.2f%n", temp.getBalance());
            System.out.printf("  - Customer: %s%n", accNames.get(index));
        } else {
            System.out.printf("Account (%d) does not exist.%n", accNum);
        }
    }

    public void readData(String path) {
        Scanner inputStream = null;

        try {
            inputStream = new Scanner(new FileInputStream(path));
        } catch (FileNotFoundException e) {
            System.out.println("File not found or could not be opened.");
            System.exit(0);
        }

        // number of Customers
        int numCust = inputStream.nextInt();

        // swallow newline character
        inputStream.nextLine();

        // I'm splitting the string up into an array using the "split()" function that I found online. We're both
        // lucky that I didn't use the "splitTokens()" function.
        for (int i = 0; i < numCust; i++) {
            String customer = inputStream.nextLine();
            String[] custSplit = customer.split(",");
            String name = custSplit[0];
            String addr = custSplit[1];
            String zipString = custSplit[2];
            int zip = Integer.parseInt(zipString);
            String ssn = custSplit[3];
            if (!ssnNumbers.contains(ssn)) {
                Customer newCustomer = new Customer(name, addr, zip, ssn);
                customers.add(newCustomer);
                names.add(name);
                addressBook.add(addr);
                ssnNumbers.add(ssn);
                numCustomers++;
            }
        }
        int numAccount = inputStream.nextInt();

        // swallow newline character
        inputStream.nextLine();

        // again with the "split()" function.
        for (int i = 0; i < numAccount; i++) {
            String account = inputStream.nextLine();
            String[] accSplit = account.split(",");
            String ssn = accSplit[0];
            String accNumString = accSplit[1];
            int accNum = Integer.parseInt(accNumString);
            String typeString = accSplit[2];
            int type = Integer.parseInt(typeString);
            String balanceString = accSplit[3];
            double balance = Double.parseDouble(balanceString);
            if (!accNums.contains(accNum)) {
                if (ssnNumbers.contains(ssn)) {
                    Customer customer = customers.get(ssnNumbers.indexOf(ssn));
                    if (type == 1 && !customer.hasChecking()) {
                        Account temp = new Account(ssn, accNum, type, balance);
                        accounts.add(temp);
                        accNums.add(accNum);
                        accNames.add(customers.get(ssnNumbers.indexOf(ssn)).getName());
                        totalBalance += balance;
                        customer.setChecking(true, accNum);
                        numAccounts++;
                    } else if (type == 2 && !customer.hasSavings()) {
                        Account temp = new Account(ssn, accNum, type, balance);
                        accounts.add(temp);
                        accNums.add(accNum);
                        accNames.add(customers.get(ssnNumbers.indexOf(ssn)).getName());
                        totalBalance += balance;
                        numAccounts++;
                        customer.setSavings(true, accNum);
                    }
                }
            }
        }
    }

    //I'm liking these for each loops now that I have gotten the hang of them
    public void bankInfo() {
        System.out.printf("Bank name: %s%n", name);
        System.out.printf("Number of Customers: %d%n", numCustomers);
        for (Customer customer : customers) {
            System.out.printf("  %s: %s%n", customer.getName(), customer.getSsn());
        }
        System.out.printf("Number of Accounts: %d%n", numAccounts);
        for (Account account : accounts) {
            System.out.printf("  %d: $%.2f%n", account.getAccNum(), account.getBalance());
        }
        System.out.printf("%nTotal Balance: $%.2f%n", totalBalance);
    }

    public void customerInfoWithSSN(int ssn) {
        String temp = String.valueOf(ssn);
        int count = 0;
        for (Customer customer : customers) {
            if (customer.getSsn().substring(7, 11).equals(temp)) {
                System.out.printf("Name: %s%n", customer.getName());
                System.out.printf("%s, %d%n", customer.getAddress(), customer.getZip());
                System.out.printf("SSN: %s%n", customer.getSsn());
                if (customer.hasChecking()) {
                    int tempNum = customer.getCheckingNum();
                    System.out.printf("Checking (%d), $%.2f%n", tempNum, accounts.get(accNums.indexOf(tempNum)).getBalance());
                }
                if (customer.hasSavings()) {
                    int tempNum = customer.getSavingsNum();
                    System.out.printf("Savings (%d), $%.2f%n", tempNum, accounts.get(accNums.indexOf(tempNum)).getBalance());
                }
                if (!customer.hasChecking() && !customer.hasSavings()){ // if if if I know, but what else could be done?
                    System.out.println("No accounts");
                }
            }
            else {
                count++;
            }
        }
        if (count == customers.size()) {
            System.out.printf("No customer with %d%n", ssn);
        }
    }
    // my code may appear larger than others because I made lists that I thought I might need later.
    public boolean removeCustomer(String ssn) {
        if (ssnNumbers.contains(ssn)) {
            int index = ssnNumbers.indexOf(ssn);
            Customer customer = customers.get(index);
            if (customer.hasChecking()) {
                int check = customer.getCheckingNum();
                int accIndex = accNums.indexOf(check);
                Transaction trans = new Transaction(accNums.get(accIndex), 3, 0.0);
                transactions.add(trans);
                System.out.printf("Account closed = Number: %d $%.2f%n",
                        accNums.get(accIndex), accounts.get(accIndex).getBalance());
                totalBalance -= accounts.get(accIndex).getBalance();
                accNums.remove(accIndex);
                accNames.remove(accIndex);
                accounts.remove(accIndex);
                numAccounts--;
            }
            if (customer.hasSavings()) {
                int save = customer.getSavingsNum();
                int accIndex = accNums.indexOf(save);
                Transaction trans = new Transaction(accNums.get(accIndex), 3, 0.0);
                transactions.add(trans);
                System.out.printf("Account closed = Number: %d $%.2f%n",
                        accNums.get(accIndex), accounts.get(accIndex).getBalance());
                totalBalance -= accounts.get(accIndex).getBalance();
                accNums.remove(accIndex);
                accNames.remove(accIndex);
                accounts.remove(accIndex);
                numAccounts--;
            }
            System.out.printf("Customer removed - SSN: ***-**-%s. Customer: %s%n",
                    ssnNumbers.get(index).substring(7, 11), names.get(index));
            customers.remove(index);
            ssnNumbers.remove(index);
            names.remove(index);
            addressBook.remove(index);
            numCustomers--;
            return true;
        }
        else  {
            System.out.printf("Customer remove failed. SSN does not exist.%n");
            return false;
        }
    }
}
