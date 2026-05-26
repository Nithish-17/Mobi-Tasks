package data;
import db.DBConnection;
import model.UnderwritingApplication;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class ApplicationDbWriter {

    public static void saveApplication(
            UnderwritingApplication application
    ) {

        String query = """
                INSERT INTO processed_underwriting_application
                (
                    application_id,
                    application_name,
                    age,
                    total_score,
                    decision
                )

                VALUES (?, ?, ?, ?, ?)
                """;

        try (

                Connection connection =
                        DBConnection.getConnection();

                PreparedStatement preparedStatement =
                        connection.prepareStatement(query)

        ) {

            preparedStatement.setString(
                    1,
                    application.getApplicationId()
            );

            preparedStatement.setString(
                    2,
                    application.getApplicationName()
            );

            preparedStatement.setInt(
                    3,
                    application.getAge()
            );

            preparedStatement.setDouble(
                    4,
                    application.getTotalScore()
            );

            preparedStatement.setString(
                    5,
                    application.getDecision()
            );

            int rowsAffected =
                    preparedStatement.executeUpdate();

        } catch (Exception e) {

            System.out.println(e.getMessage());
        }
    }
}