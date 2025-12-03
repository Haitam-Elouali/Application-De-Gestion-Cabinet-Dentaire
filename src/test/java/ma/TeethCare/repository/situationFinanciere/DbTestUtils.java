package ma.TeethCare.repository.situationFinanciere;

import ma.TeethCare.repository.common.JdbcConfig;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public final class DbTestUtils {
    private DbTestUtils() {}

    public static void cleanAll() {
        try (Connection c = JdbcConfig.getConnection();
             Statement st = c.createStatement()) {

            st.execute("SET FOREIGN_KEY_CHECKS = 0");
            st.executeUpdate("DELETE FROM situationFinanciere");
            st.execute("ALTER TABLE situationFinanciere AUTO_INCREMENT = 1");
            st.execute("SET FOREIGN_KEY_CHECKS = 1");
        } catch (SQLException e) {
            throw new RuntimeException("Nettoyage BD échoué", e);
        }
    }

    public static void seedFullDataset() {
        try (Connection c = JdbcConfig.getConnection();
             Statement st = c.createStatement()) {

            st.executeUpdate("""
                INSERT INTO situationFinanciere (id, totalDesActes, totalPaye, credit, statut, enPromo)
                VALUES
                    (1, 1500.00, 1000.00, 500.00, 'En cours', 'Non'),
                    (2, 2000.00, 2000.00, 0.00, 'Payé', 'Non'),
                    (3, 3000.00, 1500.00, 1500.00, 'En cours', 'Oui');
            """);

        } catch (SQLException e) {
            throw new RuntimeException("Seed complet échoué", e);
        }
    }
}
