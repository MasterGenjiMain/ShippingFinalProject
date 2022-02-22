package db.entity;

public class ReceiptStatus {
    private long id;
    private String statusName;

    public ReceiptStatus() {
    }

    public ReceiptStatus(long id, String statusName) {
        this.id = id;
        this.statusName = statusName;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    @Override
    public String toString() {
        return "ReceiptStatus{" +
                "id=" + id +
                ", statusName='" + statusName + '\'' +
                '}';
    }
}
