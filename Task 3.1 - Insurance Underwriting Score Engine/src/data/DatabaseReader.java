package data;

import db.DBConnection;
import model.RiskCategory;
import model.RiskFactor;
import model.UnderwritingApplication;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class DatabaseReader {

    public static List<RiskFactor> getAllFactors() {

        List<RiskFactor> factors = new ArrayList<>();

        String query = """
                SELECT
                factor_id,
                factor_name,
                category,
                weight,
                score
                FROM risk_factor
                """;

        try (
                Connection connection = DBConnection.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                ResultSet resultSet = preparedStatement.executeQuery()
        ) {

            while (resultSet.next()) {

                RiskFactor factor = new RiskFactor(
                        resultSet.getString("factor_id"),
                        resultSet.getString("factor_name"),
                        RiskCategory.valueOf(resultSet.getString("category")),
                        resultSet.getDouble("weight"),
                        resultSet.getInt("score")
                );

                factors.add(factor);
            }

        } catch (Exception e) {

            System.out.println(e.getMessage());
        }
        return factors;
    }

    public static List<UnderwritingApplication> getApplications() {

        List<UnderwritingApplication> applications = new ArrayList<>();

        String applicationQuery = """
                SELECT
                application_id,
                application_name,
                age
                FROM underwriting_application
                """;

        String factorQuery = """
                SELECT
                factor_id,
                factor_name,
                category,
                weight,
                score
                FROM risk_factor
                WHERE application_id = ?
                """;

        try (
                Connection connection = DBConnection.getConnection();
                PreparedStatement applicationStatement = connection.prepareStatement(applicationQuery);
                ResultSet applicationResult = applicationStatement.executeQuery()
        ) {

            while (applicationResult.next()) {
                String applicationId = applicationResult.getString("application_id");
                String applicationName = applicationResult.getString("application_name");
                int age = applicationResult.getInt("age");
                List<RiskFactor> factors = new ArrayList<>();

                try (
                        PreparedStatement factorStatement = connection.prepareStatement(factorQuery)
                ) {
                    factorStatement.setString(1, applicationId);
                    try (
                            ResultSet factorResult = factorStatement.executeQuery()
                    ) {
                        while (factorResult.next()) {
                            RiskFactor factor = new RiskFactor(
                                            factorResult.getString("factor_id"),
                                            factorResult.getString("factor_name"),
                                            RiskCategory.valueOf(factorResult.getString("category")),
                                            factorResult.getDouble("weight"),
                                            factorResult.getInt("score")
                                    );
                            factors.add(factor);
                        }
                    }
                }

                UnderwritingApplication application = new UnderwritingApplication(
                                applicationId,
                                applicationName,
                                age,
                                factors
                        );

                applications.add(application);
            }

        } catch (Exception e) {

            System.out.println(e.getMessage());
        }

        return applications;
    }

}