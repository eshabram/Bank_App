import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class Account extends Customer{
    private int accNum;
    private int type;
    private double balance;
    private static ArrayList<Transaction> transactions = new ArrayList<>();

    public Account() {
        this.accNum = accNum;
        this.type = 1;
        this.balance = 0.0;
    }
    public Account(String ssn, int accNum, int type, double balance) {
        this.accNum = accNum;
        this.type = type;
        this.balance = balance;
    }
    public int getAccNum() {
        return accNum;
    }
    public int getType() {
        return type;
    }
    public double getBalance() {
        return balance;
    }
    public void setBalance(double balance) {
        this.balance = balance;
    }

    public boolean equals(@NotNull Account account) {
        if (this.accNum == account.getAccNum() && this.type == account.getType() &&
        this.balance == account.getBalance()) {
            return true;
        } else return false;
    }

    public void addTransaction(Transaction trans) {
        transactions.add(trans);
    }

    public void getTransactions(int accNum) {
//        boolean flag = false;
//        for (Transaction transaction : transactions) {
//            if (transaction.getAccNum() == accNum) {
//                if (transaction.getTransType() == 1) {
//                    System.out.printf("  - Account Number: %d, Deposit ($%.2f), %s%n", transaction.getAccNum(),
//                            transaction.getAmount(), transaction.dateTime());
//                } else if (transaction.getTransType() == 2) {
//                    System.out.printf("  - Account Number: %d, Withdraw ($%.2f), %s%n", transaction.getAccNum(),
//                            transaction.getAmount(), transaction.dateTime());
//                } else {
//                    System.out.printf("  - Account Number: %d, %s, %s%n", transaction.getAccNum(),
//                            transaction.getTransType(), transaction.dateTime());
//                }
//                flag = true;
//            }
//        }
//        if (flag == false) {
//            System.out.printf("  - No transaction for account %d%n", accNum);
//        }
    }

    public String toString() {
        return String.format("Account Number: %d, Account Type: %d, Balance: $%.2f", accNum, type, balance);
    }
}
