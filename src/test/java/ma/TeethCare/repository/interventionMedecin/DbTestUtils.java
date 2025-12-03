package ma.TeethCare.repository.interventionMedecin;

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
            st.executeUpdate("DELETE FROM interventionMedecin");
            st.executeUpdate("DELETE FROM consultation");
            st.execute("ALTER TABLE interventionMedecin AUTO_INCREMENT = 1");
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
                INSERT INTO interventionMedecin (id, duree, note, resultatImagerie, consultation_id)
                VALUES
                    (1, 30, 'Extraction dentaire', 'Carie visible', 1),
                    (2, 45, 'Détartrage complet', 'Inflammations réduites', 1),
                    (3, 20, 'Examen simple', 'Pas de pathologie', 1);
            """);

        } catch (SQLException e) {
            throw new RuntimeException("Seed complet échoué", e);
        }
    }
}
