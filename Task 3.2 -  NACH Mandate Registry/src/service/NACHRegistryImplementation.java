package service;

import exception.*;
import model.NACHMandate;
import model.NACHStatus;
import model.RegistryReport;

import java.time.LocalDate;
import java.util.*;

public class NACHRegistryImplementation implements NACHRegistry {
    HashMap<String, NACHMandate> byUMRN;
    HashMap<String, List<NACHMandate>> byBankAccount;
    TreeMap<String, List<NACHMandate>> byEndDate;
    HashSet<String> revokedUMRNs;
    LinkedList<NACHMandate> pendingRegistrationQueue;

    public NACHRegistryImplementation() {
        byUMRN = new HashMap<>();
        byBankAccount = new HashMap<>();
        byEndDate = new TreeMap<>();
        revokedUMRNs = new HashSet<>();
        pendingRegistrationQueue = new LinkedList<>();
    }

    @Override
    public void submitMandate(NACHMandate mandate) throws NACHException{
        if(mandate == null){
            throw new MandateNotFoundException("Mandate not found");
        }
        if(byUMRN.containsKey(mandate.getUmrn())){
            throw new DuplicateMandateException("Mandate already exists");
        }
        byUMRN.put(mandate.getUmrn(), mandate);
        byBankAccount.computeIfAbsent(mandate.getBankAccountId(), k -> new ArrayList<>()).add(mandate);
        byEndDate.computeIfAbsent(mandate.getEndDate(), k -> new ArrayList<>()).add(mandate);
        pendingRegistrationQueue.addLast(mandate);
    }

    @Override
    public void confirmRegistration(String umrn) throws NACHException {
        NACHMandate mandate = byUMRN.get(umrn);
        if(mandate == null){
            throw new MandateNotFoundException("Mandate not found");
        }
        int size = pendingRegistrationQueue.size();
        while (size-- > 0) {
            NACHMandate current = pendingRegistrationQueue.removeFirst();
            if(current.getUmrn().equals(umrn)){
                current.setStatus(NACHStatus.ACTIVE);
            }
            else {
                pendingRegistrationQueue.addFirst(current);
            }
        }
    }

    @Override
    public void revokeMandate(String urmn) throws NACHException {
        NACHMandate mandate = byUMRN.get(urmn);
        if(mandate == null){
            throw new MandateNotFoundException("Mandate not found");
        }
        if(revokedUMRNs.contains(urmn)){
            throw new MandateAlreadyRevokedException("Mandate already revoked");
        }
        revokedUMRNs.add(urmn);
        mandate.setStatus(NACHStatus.REVOKED);
        byBankAccount
                .get(mandate.getBankAccountId())
                .remove(mandate);
    }

    @Override
    public List<NACHMandate> getExpiringMandates(String beforeDate) {
        List<NACHMandate> expiringMandates = new ArrayList<>();
        NavigableMap<String, List<NACHMandate>> expiringMandatesMap = byEndDate.headMap(beforeDate, true);
        for(Map.Entry<String, List<NACHMandate>> entry : expiringMandatesMap.entrySet()){
            expiringMandates.addAll(entry.getValue());
        }
        return expiringMandates;
    }

    @Override
    public boolean isDebitAuthorised(String urmn, double amount) throws NACHException {
        NACHMandate mandate = byUMRN.get(urmn);
        if(mandate == null){
            throw new MandateNotFoundException("Mandate not found");
        }
        if(mandate.getStatus().equals(NACHStatus.REVOKED)){
            throw new InvalidDebitException("Mandate " + mandate.getUmrn()+ ": NOT AUTHORIZED(REVOKED)");
        }
        if(!mandate.getStatus().equals(NACHStatus.ACTIVE)){
            throw new InvalidDebitException("Mandate " + mandate.getUmrn()+ ": NOT AUTHORIZED(PENDING)");
        }
        if(LocalDate.parse(mandate.getEndDate()).isBefore(LocalDate.now())){
            mandate.setStatus(NACHStatus.EXPIRED);
            throw new MadateDateExpiredException("Mandate " + mandate.getUmrn()+ ": NOT AUTHORIZED(EXPIRED)");
        }
        if(amount > mandate.getMaxDebitAmount()){
            throw new InvalidDebitException(String.format("Mandate " + mandate.getUmrn() + ": NOT AUTHORIZED(RS.%.0f > limit Rs.%.0f)", amount, mandate.getMaxDebitAmount()));
        }
        return true;
    }

    @Override
    public void processPendingRegistrations() {
        boolean approve = true;
        while (!pendingRegistrationQueue.isEmpty()) {
            NACHMandate mandate = pendingRegistrationQueue.removeFirst();
            try {

                if (approve) {
                    mandate.setStatus(NACHStatus.ACTIVE);
                } else {
                    revokeMandate(mandate.getUmrn());
                }
            }
            catch (NACHException e) {
                System.out.println(e.getMessage());
            }
            approve = !approve;
        }
    }

    @Override
    public RegistryReport generateRegistryReport() {

        int pending = 0;
        int active = 0;
        int revoked = 0;
        int expired = 0;

        double totalVolume = 0;

        for (NACHMandate mandate :byUMRN.values()) {
            switch (mandate.getStatus()) {
                case PENDING:
                    pending++;
                    break;

                case ACTIVE:
                    active++;
                    totalVolume += mandate.getMaxDebitAmount();
                    break;

                case REVOKED:
                    revoked++;
                    break;

                case EXPIRED:
                    expired++;
                    break;
            }
        }

        return new RegistryReport(
                pending,
                active,
                revoked,
                expired,
                totalVolume
        );
    }
}
