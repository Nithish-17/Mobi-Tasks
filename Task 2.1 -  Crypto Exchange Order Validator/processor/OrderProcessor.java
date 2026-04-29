package processor;

import exception.ComplianceBlockException;
import exception.CryptoOrderException;
import model.CryptoOrder;
import model.OrderRejection;
import service.CryptoOrderValidator;

import java.util.ArrayList;
import java.util.List;

public class OrderProcessor {
    private final CryptoOrderValidator validator;
    private final List<OrderRejection> rejections;

    private int acceptCount;
    private int rejectCount;
    private boolean halted = false;

    public OrderProcessor() {
        validator = new CryptoOrderValidator();
        rejections = new ArrayList<>();
    }

    public void processOrders(List<CryptoOrder> orders) {
        for (CryptoOrder order : orders) {
            if (halted) break;
            try {
                validator.validate(order);
                System.out.println(order.getOrderId() + " accepted");
                acceptCount++;
            } catch (ComplianceBlockException e) {
                halted = true;
                System.out.println(order.getOrderId() + " is halted " + e.getMessage());
            } catch (CryptoOrderException e) {
                handleRejection(order, e);
            }
        }
        printSummary();
    }

    private void handleRejection(CryptoOrder order, Exception e) {
        rejections.add(new OrderRejection(order.getOrderId(), e.getMessage()));
        System.out.println(order.getOrderId() + " Rejected - " + e.getClass().getSimpleName() + ": " + e.getMessage());
        rejectCount++;
    }

    private void printSummary() {
        System.out.println("\nSummary:");
        System.out.println("Accepted = " + acceptCount);
        System.out.println("Rejected = " + rejectCount);
        System.out.println("Halted = " + (halted ? 1 : 0));
    }
}
