package ma.TeethCare.repository.dossierMedicale;

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
            st.executeUpdate("DELETE FROM dossierMedicale");
            st.executeUpdate("DELETE FROM patient");
            st.execute("ALTER TABLE dossierMedicale AUTO_INCREMENT = 1");
            st.execute("ALTER TABLE patient AUTO_INCREMENT = 1");
            st.execute("SET FOREIGN_KEY_CHECKS = 1");
        } catch (SQLException e) {
            throw new RuntimeException("Nettoyage BD échoué", e);
        }
    }

    public static void seedFullDataset() {
        try (Connection c = JdbcConfig.getConnection();
             Statement st = c.createStatement()) {

            st.executeUpdate("""
                INSERT INTO patient (id, nom, prenom, dateNaissance, sexe, telephone, assurance)
                VALUES
                    (1, 'Amal', 'Zahra', '1995-05-12', 'Femme', '0611111111', 'CNSS'),
                    (2, 'Omar', 'Badr', '1989-09-23', 'Homme', '0622222222', 'CNOPS'),
                    (3, 'Nour', 'Chafi', '2000-02-02', 'Femme', '0633333333', 'Autre');
            """);

            st.executeUpdate("""
                INSERT INTO dossierMedicale (id, dateDeCreation, patient_id)
                VALUES
                    (1, '2025-01-01', 1),
                    (2, '2025-01-05', 2),
                    (3, '2025-01-10', 3);
            """);

        } catch (SQLException e) {
            throw new RuntimeException("Seed complet échoué", e);
        }
    }
}
