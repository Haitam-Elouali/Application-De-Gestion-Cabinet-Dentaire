package ma.TeethCare.repository.rdv;

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
            st.executeUpdate("DELETE FROM rdv");
            st.executeUpdate("DELETE FROM patient");
            st.execute("ALTER TABLE rdv AUTO_INCREMENT = 1");
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
                    (2, 'Omar', 'Badr', '1989-09-23', 'Homme', '0622222222', 'CNOPS');
            """);

            st.executeUpdate("""
                INSERT INTO rdv (numero, date, heure, statut, patient_id)
                VALUES
                    ('RDV001', '2025-02-10', '09:00:00', 'Confirmé', 1),
                    ('RDV002', '2025-02-11', '10:30:00', 'En attente', 2),
                    ('RDV003', '2025-02-12', '14:00:00', 'Annulé', 1),
                    ('RDV004', '2025-02-13', '15:30:00', 'Confirmé', 2);
            """);

        } catch (SQLException e) {
            throw new RuntimeException("Seed complet échoué", e);
        }
    }
}
