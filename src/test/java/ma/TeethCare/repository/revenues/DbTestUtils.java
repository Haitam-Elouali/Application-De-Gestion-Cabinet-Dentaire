package ma.TeethCare.repository.revenues;

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
            st.executeUpdate("DELETE FROM revenues");
            st.execute("ALTER TABLE revenues AUTO_INCREMENT = 1");
            st.execute("SET FOREIGN_KEY_CHECKS = 1");
        } catch (SQLException e) {
            throw new RuntimeException("Nettoyage BD échoué", e);
        }
    }

    public static void seedFullDataset() {
        try (Connection c = JdbcConfig.getConnection();
             Statement st = c.createStatement()) {

            st.executeUpdate("""
                INSERT INTO revenues (id, titre, description, montant, categorie, date)
                VALUES
                    (1, 'Consultations', 'Revenue des consultations', 8000.00, 'Consultation', '2025-01-15 00:00:00'),
                    (2, 'Détartrage', 'Revenue des détartrages', 3000.00, 'Détartrage', '2025-01-20 00:00:00'),
                    (3, 'Obturation', 'Revenue des obturations', 5000.00, 'Restauration', '2025-01-25 00:00:00');
            """);

        } catch (SQLException e) {
            throw new RuntimeException("Seed complet échoué", e);
        }
    }
}
