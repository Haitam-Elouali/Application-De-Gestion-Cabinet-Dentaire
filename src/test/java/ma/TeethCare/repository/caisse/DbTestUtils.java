package ma.TeethCare.repository.caisse;

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
            st.executeUpdate("DELETE FROM caisse");
            st.execute("ALTER TABLE caisse AUTO_INCREMENT = 1");
            st.execute("SET FOREIGN_KEY_CHECKS = 1");
        } catch (SQLException e) {
            throw new RuntimeException("Nettoyage BD échoué", e);
        }
    }

    public static void seedFullDataset() {
        try (Connection c = JdbcConfig.getConnection();
             Statement st = c.createStatement()) {

            st.executeUpdate("""
                INSERT INTO caisse (id, montantEntree, montantSortie, solde)
                VALUES
                    (1, 5000.00, 2000.00, 3000.00),
                    (2, 3000.00, 1500.00, 1500.00),
                    (3, 2500.00, 500.00, 2000.00);
            """);

        } catch (SQLException e) {
            throw new RuntimeException("Seed complet échoué", e);
        }
    }
}
