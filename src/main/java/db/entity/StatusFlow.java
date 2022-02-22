package db.entity;

public class StatusFlow {
    private long id;
    private long from;
    private long to;

    public StatusFlow() {
    }

    public StatusFlow(long id, long from, long to) {
        this.id = id;
        this.from = from;
        this.to = to;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getFrom() {
        return from;
    }

    public void setFrom(long from) {
        this.from = from;
    }

    public long getTo() {
        return to;
    }

    public void setTo(long to) {
        this.to = to;
    }

    @Override
    public String toString() {
        return "StatusFlow{" +
                "id=" + id +
                ", from=" + from +
                ", to=" + to +
                '}';
    }
}
