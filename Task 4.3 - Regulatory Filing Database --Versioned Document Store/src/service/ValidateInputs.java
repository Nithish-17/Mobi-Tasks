package service;

import exception.*;
import model.Entity;
import model.Filing;
import model.FilingType;
import model.RawInput;

import java.util.ArrayList;
import java.util.List;

public class ValidateInputs {

    public static List<Filing> validate(List<RawInput> rawInputs){

        int inputSerialNo = 0;
        List<Filing> validatedInput = new ArrayList<>();

        for(RawInput rawInput : rawInputs){

            inputSerialNo++;
            String entityId = rawInput.getEntityId();
            String filingType = rawInput.getFilingType();
            String period = rawInput.getPeriod();

            try {

                boolean validateEntity = false;// false default , true means found and false is not found then invalid

                for (Entity entity : Entity.values()) {
                    if (Entity.valueOf(entityId) == entity) {
                        validateEntity = true;
                        break;
                    }
                }

                if (!validateEntity) {
                    throw new InvalidEntityIdException(String.format("Invalid Entity Id: %s ---- Skipping input %d", entityId, inputSerialNo));
                }


                boolean validateFilingType = false;

                for (FilingType type : FilingType.values()) {

                    if (FilingType.valueOf(filingType) == type) {
                        validateFilingType = true;
                        break;
                    }
                }

                if (!validateFilingType) {
                    throw new InvalidFilingTypeException(String.format("Invalid Filing Type : %s ------Skipping input %d", filingType, inputSerialNo));
                }


                boolean validatePeriod = period.matches("^Q[1-4]-\\d{4}$");

                if (!validatePeriod) {
                    throw new InvalidPeriodException(String.format("Invalid Period : %s ------Skipping input %d", period, inputSerialNo));
                }

                validatedInput.add(new Filing(Entity.valueOf(entityId),FilingType.valueOf(filingType),period));
            }

            catch (RegulatoryException e){
                System.out.println(e.getMessage());
            }

        }
        return validatedInput;
    }

    public static void validateBaseId(String baseId)
            throws InvalidBaseIdException {

        String[] parts = baseId.split("_");

        if(parts.length != 2) {

            throw new InvalidBaseIdException(
                    "Invalid baseId format");
        }

        try {

            Entity.valueOf(parts[0]);

            FilingType.valueOf(parts[1]);

        }
        catch (IllegalArgumentException e) {

            throw new InvalidBaseIdException(
                    "Invalid entity or filing type");
        }
    }
}
