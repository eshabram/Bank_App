import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class Account {
    private int accNum;
    private int type;
    private double balance;


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

    public String toString() {
        return String.format("Account Number: %d, Account Type: %d, Balance: $%.2f", accNum, type, balance);
    }
}
