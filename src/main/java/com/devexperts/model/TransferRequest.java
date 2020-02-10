package com.devexperts.model;

public class TransferRequest {
    private long sourceId;
    private long targetId;
    private double amount;

    public long getSourceId() {
        return sourceId;
    }

    public long getTargetId() {
        return targetId;
    }

    public double getAmount() {
        return amount;
    }

    public void setSourceId(long sourceId) {
        this.sourceId = sourceId;
    }

    public void setTargetId(long targetId) {
        this.targetId = targetId;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
