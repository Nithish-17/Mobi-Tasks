package service;

import Util.InputUtil;
import exception.*;
import model.Policy;
import model.StatusState;

import java.time.LocalDate;
import java.util.Random;

public class PolicyService {

    int collected;
    int grace;
    int lapsed;
    int invalid;
    int mismatch;

    public int getCollected() { return collected; }
    public int getGrace() { return grace; }
    public int getLapsed() { return lapsed; }
    public int getInvalid() { return invalid; }
    public int getMismatch() { return mismatch; }

    Random rand = new Random();

    private void collectPremium(Policy policy) throws PremiumCollectionException {

        LocalDate today = LocalDate.now();

        // 1. Payment method invalid
        if(!policy.isBankApproved() || today.isAfter(policy.getExpiryDate())){
            throw new PaymentMethodInvalidException(policy.getPolicyId() + " PaymentMethodInvalid — auto-debit suspended for " + policy.getCustomerId());
        }

        if(policy.getStatus() == StatusState.ACTIVE){
            if(today.isAfter(policy.getDueDate())){
                LocalDate graceEndDate = policy.getDueDate().plusDays(10);
                // 2. Grace activation
                throw new GracePeriodActiveException(policy.getPolicyId() + " GRACE — retry scheduled for " + graceEndDate, graceEndDate);
            }
        }

        if(policy.getStatus() == StatusState.GRACE){
            if(today.isAfter(policy.getGraceEndDate())){
                // 3. Lapsed
                throw new PolicyLapsedException(policy.getPolicyId() + " LAPSED — renewal notice sent to " + policy.getCustomerId(),today);
            }
        }

        double collectedAmount = InputUtil.getDouble("Enter collected amount from bank for " + policy.getCustomerId() + ": ");
        if(collectedAmount < policy.getPremiumAmount()){
            throw new PremiumMismatchException(policy.getPolicyId() + " PremiumMismatch — due Rs." +policy.getPremiumAmount()+ " collected Rs." + collectedAmount,policy.getPremiumAmount(),collectedAmount);
        }
        else if(collectedAmount == policy.getPremiumAmount()){
            policy.setStatus(StatusState.ACTIVE);
        }

    }

    public void processCollection(Policy policy)
            throws PremiumCollectionException {

        try {
            collectPremium(policy);
            collected++;
        }

        catch (PaymentMethodInvalidException e){
            policy.setStatus(StatusState.SUSPENDED);
            invalid++;
            throw e;
        }
        catch (PremiumMismatchException e) {
            policy.setStatus(StatusState.MISMATCH);
            mismatch++;
            throw e;
        }
        catch (GracePeriodActiveException e) {
            policy.setStatus(StatusState.GRACE);
            grace++;
            throw e;
        }
        catch (PolicyLapsedException e) {
            policy.setStatus(StatusState.LAPSED);
            lapsed++;

            boolean flag = rand.nextBoolean();
            if (flag) {
                throw new RenewalProcessingException(policy.getPolicyId() + " LAPSED | Renewal Notice Failed (Email Service Down)");
            }
            throw e;
        }
    }
}