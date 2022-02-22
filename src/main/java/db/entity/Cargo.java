package db.entity;

public class Cargo {
    private long id;
    private String name;
    private String description;
    private long deliveryOrderId;

    public Cargo() {
    }

    public Cargo(long id, String name, String description, long deliveryOrderId) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.deliveryOrderId = deliveryOrderId;
    }

    public Cargo(long id, String name, long deliveryOrderId) {
        this.id = id;
        this.name = name;
        this.deliveryOrderId = deliveryOrderId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getDeliveryOrderId() {
        return deliveryOrderId;
    }

    public void setDeliveryOrderId(long deliveryOrderId) {
        this.deliveryOrderId = deliveryOrderId;
    }

    @Override
    public String toString() {
        return "Cargo{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", deliveryOrderId=" + deliveryOrderId +
                '}';
    }
}
