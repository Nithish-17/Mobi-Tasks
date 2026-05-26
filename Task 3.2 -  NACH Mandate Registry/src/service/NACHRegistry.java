package service;

import exception.MandateNotFoundException;
import exception.NACHException;
import model.NACHMandate;
import model.RegistryReport;

import java.util.List;

public interface NACHRegistry {
    void submitMandate(NACHMandate mandate) throws NACHException;
    void confirmRegistration(String urmn) throws NACHException;
    void revokeMandate(String urmn) throws NACHException;
    List<NACHMandate> getExpiringMandates(String beforeDate);
    boolean isDebitAuthorised(String umrn, double amount) throws NACHException;
    void  processPendingRegistrations();
    RegistryReport generateRegistryReport();

}
