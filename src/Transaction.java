import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Transaction {
    private int accNum;
    private double amount;
    private int transType;
    private String time;

    public Transaction() {
        accNum = 0;
        transType = 0;
        amount = 0.0;
        dateTime();
    }

    public Transaction(int accNum, int transType, double amount) {
        this.accNum = accNum;
        this.transType = transType;
        this.amount = amount;
        this.time = dateTime();
    }
    public int getTransAccNum() {return accNum;}
    public int getTransType() {
        return transType;
    }
    public double getAmount() {
        return amount;
    }
    public void setTransType() {
        this.transType = transType;
    }

    public String dateTime() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd, HH:mm:ss");
        String formatDateTime = now.format(formatter);
        return formatDateTime;
    }
}
