package model;

public class OrderRejection {

    private final String orderId;
    private final String reason;

    public OrderRejection(String orderId, String reason) {
        this.orderId = orderId;
        this.reason = reason;
    }

    public String getOrderId() {
        return orderId;
    }

    public String getReason() {
        return reason;
    }
}