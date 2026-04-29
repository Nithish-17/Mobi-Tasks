package service;

import exception.*;
import model.CryptoOrder;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class CryptoOrderValidator {
    private static final Set<String> VALID_WALLETS = new HashSet<>();
    private static final Set<String> BLOCKED_WALLETS = new HashSet<>();
    private static final Set<String> SUSPENDED_PAIRS = new HashSet<>();

    static {
        VALID_WALLETS.addAll(Arrays.asList("0x123","0xABC"));
        BLOCKED_WALLETS.add("0x999");
        SUSPENDED_PAIRS.add("BTC/USDT");
    }
    void validateWallet(CryptoOrder order) throws CryptoOrderException{
        String address = order.getWalletAddress();
        if(address == null || !address.startsWith("0x") || !VALID_WALLETS.contains(address)){
            throw new InvalidWalletException("Wallet " + address + " not registered");
        }
    }

    void checkBalance(CryptoOrder order) throws CryptoOrderException{
        double balance = 4000; //dummy balance;
        double avaliableCrypto = 2.0;
        double requiredToPurchase = order.getQuantity() * order.getPricePerUnit();
        if(order.getSide().equals("BUY") && balance < requiredToPurchase){
            throw new InsufficientCryptoBalance("need " + (requiredToPurchase - balance) + " have " + balance + " to buy" ,order.getSide(),(requiredToPurchase - balance),balance);
        }
        else if(order.getSide().equals("SELL") && order.getQuantity() > avaliableCrypto){
            throw new InsufficientCryptoBalance("need " + (order.getQuantity() - avaliableCrypto) + " have " + avaliableCrypto + " to sell", order.getSide(), (requiredToPurchase - balance),avaliableCrypto);
        }
    }

    void checkCompliance(CryptoOrder order){
            if(BLOCKED_WALLETS.contains(order.getWalletAddress()))
                throw new ComplianceBlockException(" wallet linked to restricted jurisdiction : " + order.getWalletAddress());
    }

    void checkOrderSize(CryptoOrder order) throws CryptoOrderException{
        if(order.getQuantity() < 0.001){
            throw new OrderSizeException(order.getQuantity() + " order quantity too small minimum 0.001");
        }
    }

    void checkTradingPair(CryptoOrder order) throws CryptoOrderException{
        if(SUSPENDED_PAIRS.contains(order.getTradingPair())){
            throw new TradingPairSuspendedException(order.getTradingPair() + " trading is currently suspended resumes 15:00",order.getTradingPair(),"15:00");
        }

    }
    public void validate(CryptoOrder order) throws CryptoOrderException {
        checkCompliance(order);
        validateWallet(order);
        checkTradingPair(order);
        checkOrderSize(order);
        checkBalance(order);
    }
}
