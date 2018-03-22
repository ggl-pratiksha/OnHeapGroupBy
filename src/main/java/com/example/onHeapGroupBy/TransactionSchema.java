package com.example.onHeapGroupBy;

public class TransactionSchema {

    private int id;
    private long price;

    public TransactionSchema() { }

    public TransactionSchema(int id, long price) {
        this.id = id;
        this.price = price;
    }

    @Override
    public String toString() {
        return "TransactionSchema{" +
                "id=" + id +
                ", price=" + price +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }
}
