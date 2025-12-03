package ma.TeethCare.repository.charges;

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
            st.executeUpdate("DELETE FROM charges");
            st.execute("ALTER TABLE charges AUTO_INCREMENT = 1");
            st.execute("SET FOREIGN_KEY_CHECKS = 1");
        } catch (SQLException e) {
            throw new RuntimeException("Nettoyage BD échoué", e);
        }
    }

    public static void seedFullDataset() {
        try (Connection c = JdbcConfig.getConnection();
             Statement st = c.createStatement()) {

            st.executeUpdate("""
                INSERT INTO charges (id, titre, description, montant, categorie, date)
                VALUES
                    (1, 'Loyer', 'Loyer du cabinet', 3000.00, 'Immobilier', '2025-01-01 00:00:00'),
                    (2, 'Salaires', 'Salaires du personnel', 15000.00, 'Ressources', '2025-01-05 00:00:00'),
                    (3, 'Fournitures', 'Achat de matériel dentaire', 2000.00, 'Fournitures', '2025-01-10 00:00:00');
            """);

        } catch (SQLException e) {
            throw new RuntimeException("Seed complet échoué", e);
        }
    }
}
