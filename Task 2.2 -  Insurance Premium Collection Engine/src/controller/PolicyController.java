package controller;

import exception.PremiumCollectionException;
import exception.RenewalProcessingException;
import model.Policy;
import service.PolicyService;

import java.util.List;


public class PolicyController {

    private final PolicyService service = new PolicyService();

    public void processBulk(List<Policy> policies) {

        for (Policy policy : policies) {
            try {
                service.processCollection(policy);
                System.out.println(
                        policy.getPolicyId() + " COLLECTED Rs." + policy.getPremiumAmount()
                );
            }
            catch (PremiumCollectionException | RenewalProcessingException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public void generateCollectionReport() {

        System.out.printf(
                "COLLECTED=%d | GRACE=%d | LAPSED=%d | INVALID=%d | MISMATCH=%d%n",
                service.getCollected(),
                service.getGrace(),
                service.getLapsed(),
                service.getInvalid(),
                service.getMismatch()
        );
    }
}