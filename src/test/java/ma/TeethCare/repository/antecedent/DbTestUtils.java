package ma.TeethCare.repository.antecedent;

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
            st.executeUpdate("DELETE FROM patient_antecedent");
            st.executeUpdate("DELETE FROM antecedants");
            st.execute("ALTER TABLE antecedants AUTO_INCREMENT = 1");
            st.execute("SET FOREIGN_KEY_CHECKS = 1");
        } catch (SQLException e) {
            throw new RuntimeException("Nettoyage BD échoué", e);
        }
    }

    public static void seedFullDataset() {
        try (Connection c = JdbcConfig.getConnection();
             Statement st = c.createStatement()) {

            st.executeUpdate("""
                INSERT INTO antecedants (nom, categorie, niveauDeRisque)
                VALUES
                    ('Allergie pénicilline', 'ALLERGIE', 'CRITIQUE'),
                    ('Allergie latex', 'ALLERGIE', 'ELEVE'),
                    ('Diabète type 2', 'MALADIE', 'MODERE'),
                    ('Hypertension', 'MALADIE', 'MODERE'),
                    ('Asthme', 'MALADIE', 'ELEVE');
            """);

        } catch (SQLException e) {
            throw new RuntimeException("Seed complet échoué", e);
        }
    }
}
