package controller;

import data.MandateData;
import exception.NACHException;
import model.NACHMandate;
import model.RegistryReport;
import service.NACHRegistryImplementation;

import java.util.List;

public class Controller {

    private NACHRegistryImplementation registry = new NACHRegistryImplementation();

    public void run() {
            List<NACHMandate> mandates = MandateData.getMandates();
            for (NACHMandate mandate : mandates) {
                try {
                    registry.submitMandate(mandate);
                }
                catch (NACHException e){
                    System.out.println(e.getMessage());
                }
            }
            //making 10 mandates active
            for (int i = 1; i <= 10; i++) {
                try {
                    registry.confirmRegistration(String.format("UMRN%03d", i));
                }
                catch (NACHException e) {
                    System.out.println(e.getMessage());
                }
            }


            //revoking 3 mandates
            for(int i=8; i<=10; i++){
                try {
                    registry.revokeMandate(String.format("UMRN%03d", i));
                }
                catch (NACHException e) {
                    System.out.println(e.getMessage());
                }
            }

            List<NACHMandate> expiringMandates = registry.getExpiringMandates("2024-06-30");
            System.out.println("Expiring mandates before 2024-06-30 : ");
            for (NACHMandate mandate : expiringMandates) {
                System.out.println(
                                mandate.getUmrn()
                                + " ("
                                + mandate.getEndDate()
                                + ")"
                );
            }
            int amount = 1000;
            //this pending call placement give different output
            registry.processPendingRegistrations();
            for(NACHMandate mandate : mandates) {
                try {
                    String umrn = mandate.getUmrn();
                    if (registry.isDebitAuthorised(umrn, amount)) {
                        System.out.println("Mandate " + umrn + ": AUTHORISED (ACTIVE, Rs." + amount + " <= Rs." + mandate.getMaxDebitAmount() + " limit)");
                        }
                }
                catch (NACHException e) {
                    System.out.println(e.getMessage());
                }
                amount += 1000;
            }

            RegistryReport report =registry.generateRegistryReport();
            System.out.println("PENDING_REGISTRATION : "+ report.getPending());
            System.out.println("ACTIVE : "+ report.getActive());
            System.out.println("REVOKED : "+ report.getRevoked());
            System.out.println("EXPIRED : "+ report.getExpired());
            System.out.println("Total authorised volume : Rs."+ report.getTotalAuthorisedVolume()+ "/month");
        }
    }
