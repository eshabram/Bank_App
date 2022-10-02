import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.Array;
import java.util.ArrayList;
import java.util.Scanner;

public class Bank extends Account{
    private String name;
    private static double totalBalance = 0;
    private static int numAccounts;
    private static int numCustomers;

    private ArrayList<Account> accounts = new ArrayList<>();
    private ArrayList<Customer> customers = new ArrayList<>();
    private ArrayList<String> names = new ArrayList<>();
    private ArrayList<Integer> accNums = new ArrayList<>();
    private ArrayList<String> ssnNumbers = new ArrayList<>();
    private ArrayList<String> addressBook = new ArrayList<>();
    private ArrayList<Transaction> transactions = new ArrayList<>();

    public Bank() {
        this.name = "Default Bank";
    }

    public Bank(String name) {
        this.name = name;
    }

    public boolean newAccount(String ssn, int accNum, int type, double balance) {
        if (!accNums.contains(accNum)) {
            if (ssnNumbers.contains(ssn) && accounts.size() > 1) {
                if (accounts.get(ssnNumbers.indexOf(ssn)).getType() != type) {
                    Account temp = new Account(ssn, accNum, type, balance);
                    accounts.add(temp);
                    accNums.add(temp.getAccNum());
                    totalBalance += balance;
                    numAccounts++;
                    return true;
                } else return false;
            } else {
                Account temp = new Account(ssn, accNum, type, balance);
                accounts.add(temp);
                accNums.add(temp.getAccNum());
                totalBalance += balance;
                numAccounts++;
                return true;
            }
        } else return false;
    }

    public boolean newCustomer(String name, String addr, int zip, String ssn) {
        if (!ssnNumbers.contains(ssn)) {
            Customer newCustomer = new Customer(name, addr, zip, ssn);
            customers.add(newCustomer);
            names.add(name);
            addressBook.add(addr);
            ssnNumbers.add(ssn);
            numCustomers++;
            return true;
        }
        else return false;
    }

    public boolean closeAccount(int accNum) {
        if (accNums.contains(accNum)) {
            Transaction trans = new Transaction(accNum, 3, 0);
            accounts.get(accNums.indexOf(accNum)).addTransaction(trans);
            int index = accNums.indexOf(accNum);
            totalBalance -= accounts.get(index).getBalance();
            accounts.remove(index);
            accNums.remove(index);
            names.remove(index);
            ssnNumbers.remove(index);
            addressBook.remove(index);
            numAccounts--;
            return true;
        } else return false;
    }

    public boolean deposit(int accNum, double deposit) {
        Account temp = new Account();
        temp = accounts.get(accNums.indexOf(accNum));
        if (accNums.contains(accNum)) {
            Transaction trans = new Transaction(accNum, 1, deposit);
            temp.addTransaction(trans);
            temp.setBalance(temp.getBalance()+deposit);
            return true;
        } else return false;
    }
    public boolean withdraw(int accNum, double withdraw) {
        Account temp = new Account();
        temp = accounts.get(accNums.indexOf(accNum));
        if (accNums.contains(accNum)) {
            Transaction trans = new Transaction(accNum, 2, withdraw);
            temp.addTransaction(trans);
            temp.setBalance(temp.getBalance()-withdraw);
            return true;  //
        } else return false;
    }

    public void transaction(int accNum) {
        boolean flag = false;
        for (Transaction transaction : transactions) {
            if (transaction.getAccNum() == accNum) {
                if (transaction.getTransType() == 1) {
                    System.out.printf("  - Account Number: %d, Deposit ($%.2f), %s%n", transaction.getAccNum(),
                            transaction.getAmount(), transaction.dateTime());
                } else if (transaction.getTransType() == 2) {
                    System.out.printf("  - Account Number: %d, Withdraw ($%.2f), %s%n", transaction.getAccNum(),
                            transaction.getAmount(), transaction.dateTime());
                } else {
                    System.out.printf("  - Account Number: %d, %s, %s%n", transaction.getAccNum(),
                            transaction.getTransType(), transaction.dateTime());
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
            System.out.printf("  - Customer: %s%n", names.get(index));
        } else {
            System.out.println("");
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

        // first customer... (you will most likely want to use a loop here using the number above...)
        for (int i = 0; i < numCust; i++) {
            String customer = inputStream.nextLine();
            String[] custSplit = customer.split(",");
            String name = custSplit[0];
            String addr = custSplit[1];
            String zipString = custSplit[2];
            int zip = Integer.parseInt(zipString);
            String ssn = custSplit[3];
            newCustomer(name, addr, zip, ssn);
        }
        int numAccount = inputStream.nextInt();

        // swallow newline character
        inputStream.nextLine();
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
            newAccount(ssn, accNum, type, balance);
        }
    }

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

}
