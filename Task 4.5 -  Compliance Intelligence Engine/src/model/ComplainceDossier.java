package model;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ComplainceDossier {

    private LinkedHashMap<String, List<String>> records;

    public ComplainceDossier() {
        records = new LinkedHashMap<>();
    }

    public void addRecord(String source, String record){

        records.computeIfAbsent(source, k -> new ArrayList<>()).add(record);
    }

    public LinkedHashMap<String, List<String>> getRecords(){
        return records;
    }

    @Override
    public String toString() {

        StringBuilder builder = new StringBuilder();

        for(Map.Entry<String, List<String>> entry : records.entrySet()) {

            builder.append("\n========== ").append(entry.getKey().toUpperCase()).append(" ==========\n");

            for(String record : entry.getValue()) {

                builder.append(record).append("\n");
            }
        }

        return builder.toString();
    }

}
