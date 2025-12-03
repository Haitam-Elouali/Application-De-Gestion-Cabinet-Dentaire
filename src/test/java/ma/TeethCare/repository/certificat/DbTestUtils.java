package ma.TeethCare.repository.certificat;

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
            st.executeUpdate("DELETE FROM certificat");
            st.executeUpdate("DELETE FROM consultation");
            st.execute("ALTER TABLE certificat AUTO_INCREMENT = 1");
            st.execute("ALTER TABLE consultation AUTO_INCREMENT = 1");
            st.execute("SET FOREIGN_KEY_CHECKS = 1");
        } catch (SQLException e) {
            throw new RuntimeException("Nettoyage BD échoué", e);
        }
    }

    public static void seedFullDataset() {
        try (Connection c = JdbcConfig.getConnection();
             Statement st = c.createStatement()) {

            st.executeUpdate("""
                INSERT INTO consultation (id, date, motif, statut)
                VALUES (1, '2025-02-01', 'Douleur dent', 'Terminée');
            """);

            st.executeUpdate("""
                INSERT INTO certificat (id, type, dateDebut, dateFin, duree, note, consultation_id)
                VALUES
                    (1, 'Arrêt de travail', '2025-02-01', '2025-02-05', 4, 'Douleur dentaire', 1),
                    (2, 'Justificatif', '2025-02-02', '2025-02-02', 1, 'Consultation dentaire', 1),
                    (3, 'Certificat médical', '2025-02-03', '2025-02-08', 5, 'Suivi post-opératoire', 1);
            """);

        } catch (SQLException e) {
            throw new RuntimeException("Seed complet échoué", e);
        }
    }
}
