import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Transaction extends Account{
    private int accNumber;
    private double amount;
    private int transType;
    private String time;

    public Transaction() {
        this.accNumber = 0;
        this.transType = 0;
        this.amount = 0.0;
        this.dateTime();
    }

    public Transaction(int accNumber, int transType, double amount) {
        this.accNumber = accNumber;
        this.transType = transType;
        this.amount = amount;
        this.time = dateTime();
    }
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
